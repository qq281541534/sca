package com.theone.contentcenter.rocketmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.StringUtils;
import com.theone.contentcenter.dao.messaging.RocketmqTransactionLogMapper;
import com.theone.contentcenter.domain.dto.content.ShareAuditDTO;
import com.theone.contentcenter.domain.entity.messaging.RocketmqTransactionLog;
import com.theone.contentcenter.domain.enums.UserAddBonusMsgEnum;
import com.theone.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;


/**
 * 添加积分的本地事务监听器
 *
 * @author liuyu
 */
@RocketMQTransactionListener(txProducerGroup = "tx-add-bonus-group")
@RequiredArgsConstructor
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {

    private final ShareService shareService;
    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;
    /**
     * 执行本地事务并记录一个本地事务日志，成功后二次确认提交/回滚
     *
     * @param msg 发送给RocketMQ的消息体MessageBuilder
     * @param arg 实体对象，stream编程模型中该参数没用
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        MessageHeaders headers = msg.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer shareId = Integer.valueOf((String) headers.get("share_id"));
        String event = (String) headers.get("event");
        try {
            if(StringUtils.equals(UserAddBonusMsgEnum.CONTRIBUTE.toString(), event)){
                // 这里注意需要将dto对象反序列化，容易出错
                String dtoString = (String) headers.get("dto");
                ShareAuditDTO shareAuditDTO = JSON.parseObject(dtoString, ShareAuditDTO.class);
                shareService.auditByIdWithRocketMqLog(shareId, shareAuditDTO, transactionId);
            } if (StringUtils.equals(UserAddBonusMsgEnum.EXCHANGE.toString(), event)) {
                // 兑换完成后增加本地事务到日志
                Integer userId = Integer.valueOf((String) headers.get("userId"));
                shareService.exchangeByIdWithRocketMqLog(shareId, userId, transactionId);
            }
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 检查本地事务是否执行成功
     * 本方法的作用：
     * RocketMQ如果一直未收到二次确认提交/回滚的消息，则会主动像生产者请求回查(本方法)，根据生产者查询本地事务记录的结果再次提交/回滚消息
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        MessageHeaders headers = msg.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);

        RocketmqTransactionLog rocketmqTransactionLog = rocketmqTransactionLogMapper.selectOne(
                RocketmqTransactionLog.builder()
                        .transactionId(transactionId)
                        .build()
        );
        if (rocketmqTransactionLog != null) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
