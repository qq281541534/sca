package com.theone.contentcenter.service.content;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.theone.contentcenter.dao.content.MidUserShareMapper;
import com.theone.contentcenter.dao.content.ShareMapper;
import com.theone.contentcenter.dao.messaging.RocketmqTransactionLogMapper;
import com.theone.contentcenter.domain.dto.content.ShareAuditDTO;
import com.theone.contentcenter.domain.dto.content.ShareDTO;
import com.theone.contentcenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.theone.contentcenter.domain.dto.user.UserDTO;
import com.theone.contentcenter.domain.entity.content.MidUserShare;
import com.theone.contentcenter.domain.entity.content.Share;
import com.theone.contentcenter.domain.entity.messaging.RocketmqTransactionLog;
import com.theone.contentcenter.domain.enums.AuditStatusEnum;
import com.theone.contentcenter.domain.enums.UserAddBonusMsgEnum;
import com.theone.contentcenter.feignclient.UserCenterFeignClient;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 分享的服务类
 *
 * @author liuyu
 */
@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareMapper shareMapper;
    private final UserCenterFeignClient userCenterFeignClient;
    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;
    private final Source source;
    private final MidUserShareMapper midUserShareMapper;

    /**
     * 通过ID查询分享
     *
     * @param id 分享ID
     * @return ShareDTO
     */
    public ShareDTO findById(Integer id) {
        // 获取share
        Share share = shareMapper.selectByPrimaryKey(id);

        // 通过share中的userid获取用户信息
        Integer userId = share.getUserId();
        UserDTO userDTO = this.userCenterFeignClient.findById(userId);

        // 填充shareDTO
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setUserId(userDTO.getId());
        shareDTO.setWxNickname(userDTO.getWxNickname());
        return shareDTO;
    }

    /**
     * 通过ID审核分享
     * Tips: 使用RocketMQ实现审核分享成功后添加用户积分的分布式事务
     *
     * @param id       分享ID
     * @param auditDTO 审核DTO
     * @return Share
     */
    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
        Share share = shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法，该分享不存在！");
        }

        if (!Objects.equal(AuditStatusEnum.NOT_YET.toString(), share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法，该分享已审核通过或审核不通过！");
        }

        // 1.PASS 审核并添加积分。 发送半消息到RocketMQ并给本地的事务监听器
        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {
            String transactionId = UUID.randomUUID().toString();

            // 使用spring cloud stream实现分布式事务
            String event = UserAddBonusMsgEnum.CONTRIBUTE.toString();
            this.source.output().send(
                    MessageBuilder
                            .withPayload(
                                    UserAddBonusMsgDTO.builder()
                                            .userId(share.getUserId())
                                            .bonus(100)
                                            .event(event)
                                            .description(UserAddBonusMsgEnum.CONTRIBUTE.getValue())
                                            .build()
                            )
                            .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                            .setHeader("event", event)
                            .setHeader("share_id", id)
                            .setHeader("dto", JSON.toJSON(auditDTO))
                            .build()
            );
        }
        // 非PASS 只审核
        else {
            auditByIdInDB(id, auditDTO);
        }
        return share;
    }

    /**
     * 审核落库
     *
     * @param id
     * @param auditDTO 审核DTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdInDB(Integer id, ShareAuditDTO auditDTO) {
        Share share = Share.builder()
                .id(id)
                .auditStatus(auditDTO.getAuditStatusEnum().toString())
                .reason(auditDTO.getReason())
                .build();
        shareMapper.updateByPrimaryKeySelective(share);
    }

    /**
     * 审核落库并增加本地事务日志落库
     *
     * @param id
     * @param shareAuditDTO
     * @param transactionId
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdWithRocketMqLog(Integer id, ShareAuditDTO shareAuditDTO, String transactionId) {
        this.auditByIdInDB(id, shareAuditDTO);
        this.rocketmqTransactionLogMapper.insertSelective(
                RocketmqTransactionLog.builder()
                        .transactionId(transactionId)
                        .log(UserAddBonusMsgEnum.CONTRIBUTE.getValue())
                        .build()
        );
    }

    /**
     * 通过标题分页查询列表
     *
     * @param title
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageInfo<Share> query(String title, Integer pageNo, Integer pageSize, Integer userId) {
        // 自动切入下面的查询语句，拼接成分页SQL
        PageHelper.startPage(pageNo, pageSize);
        // 不分页的SQL查询
        List<Share> shares = this.shareMapper.selectByParam(title);
        List<Share> sharesDealed = Lists.newArrayList();
        // 1. 验证用户是否登录，若果未登陆将所有download url设置未空
        if (userId == null) {
            sharesDealed = shares.stream()
                    .peek(share -> {
                        share.setDownloadUrl("");
                    })
                    .collect(Collectors.toList());
        } else {
            // 2. 如果已登录，则验证当前分享是否兑换过，未兑换过也要将download url设置为空
            sharesDealed = shares.stream()
                    .peek(share -> {
                        MidUserShare midUserShare = this.midUserShareMapper.selectOne(
                                MidUserShare.builder()
                                        .userId(userId)
                                        .shareId(share.getId())
                                        .build()
                        );
                        if (midUserShare == null) {
                            share.setDownloadUrl("");
                        }
                    })
                    .collect(Collectors.toList());
        }

        return new PageInfo<>(sharesDealed);
    }

    /**
     * 通过ID兑换分享
     *
     * @param id
     * @param request
     * @return
     */
    public Share exchangeById(String id, HttpServletRequest request) {
        // 1. 查询该分享是否存在
        Share share = shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("分享不存在");
        }

        // 2. 查询该用户是否兑换过该分享，兑换过则直接返回分享
        Integer userId = (Integer) request.getAttribute("id");
        Integer shareId = share.getId();
        MidUserShare midUserShare = this.midUserShareMapper.selectOne(
                MidUserShare.builder()
                        .userId(userId)
                        .shareId(shareId)
                        .build()
        );
        if (midUserShare != null) {
            return share;
        }

        // 3. 未兑换过，则先根据当前登录的用户id，查询积分是否够用
        Integer price = share.getPrice();
        UserDTO userDTO = this.userCenterFeignClient.findById(userId);
        // 不够用，抛异常
        if (price > userDTO.getBonus()) {
            throw new IllegalArgumentException("用户积分不够用！");
        }

        // 4 够用，则兑换并使用分布式事务扣除用户积分
        String transactionId = UUID.randomUUID().toString();
        String event = UserAddBonusMsgEnum.EXCHANGE.toString();
        this.source.output().send(
                MessageBuilder
                        .withPayload(
                                UserAddBonusMsgDTO.builder()
                                        .userId(userId)
                                        .bonus(0 - price)
                                        .event(event)
                                        .description(UserAddBonusMsgEnum.EXCHANGE.getValue())
                                        .build()
                        )
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                        .setHeader("event", event)
                        .setHeader("share_id", id)
                        .setHeader("userId", userId)
                        .build()
        );

        return share;
    }

    @Transactional(rollbackFor = Exception.class)
    public void exchangeByIdWithRocketMqLog(Integer shareId, Integer userId, String transactionId) {
        // 4. 添加兑换记录
        this.midUserShareMapper.insert(
                MidUserShare.builder()
                        .shareId(shareId)
                        .userId(userId)
                        .build()
        );
        this.rocketmqTransactionLogMapper.insertSelective(
                RocketmqTransactionLog.builder()
                        .transactionId(transactionId)
                        .log(UserAddBonusMsgEnum.EXCHANGE.getValue())
                        .build()
        );
    }
}
