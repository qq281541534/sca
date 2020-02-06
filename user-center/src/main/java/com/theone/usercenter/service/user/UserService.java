package com.theone.usercenter.service.user;

import com.theone.apimodel.dto.UserDTO;
import com.theone.apimodel.rpc.UserServiceApi;
import com.theone.usercenter.dao.bouns.BounsEventLogMapper;
import com.theone.usercenter.dao.user.UserMapper;
import com.theone.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.theone.usercenter.domain.dto.user.UserLoginDTO;
import com.theone.usercenter.domain.entity.bouns.BounsEventLog;
import com.theone.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户服务
 * 提供RPC服务，@Service 不是org.springframework.stereotype.Service, 是dubbo包下org.apache.dubbo.config.annotation.Service
 *
 * @author liuyu
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserServiceApi {

    private final UserMapper userMapper;
    private final BounsEventLogMapper bounsEventLogMapper;

    @Override
    public UserDTO findById(Integer id){
        User user = this.userMapper.selectByPrimaryKey(id);
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            BeanUtils.copyProperties(user, userDTO);
        }
        return userDTO;
    }

    /**
     * 登陆
     *
     * @param loginDTO
     * @param openid
     * @return
     */
    public User login(UserLoginDTO loginDTO, String openid){
        User user = this.userMapper.selectOne(
                User.builder()
                        .wxId(openid)
                        .build()
        );
        if(user == null){
            this.userMapper.insertSelective(
                user = User.builder()
                      .wxId(openid)
                      .avatarUrl(loginDTO.getAvatarUrl())
                      .wxNickname(loginDTO.getWxNickName())
                      .createTime(new Date())
                      .updateTime(new Date())
                      .bonus(300)
                      .roles("user")
                    .build()
            );
        }
        return user;
    }

    /**
     * 增加积分
     *
     * @param userAddBonusMsgDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void addBonus(UserAddBonusMsgDTO userAddBonusMsgDTO) {
        // 当收到消息的时候，执行业务
        Integer bonus = userAddBonusMsgDTO.getBonus();
        Integer userId = userAddBonusMsgDTO.getUserId();
        // 1. 为用户加积分
        User user = userMapper.selectByPrimaryKey(userId);
        if(user != null){

            user.setBonus(user.getBonus() + bonus);
            userMapper.updateByPrimaryKeySelective(user);
        }
        // 2. 记录日志
        bounsEventLogMapper.insertSelective(
                BounsEventLog.builder()
                        .userId(userId)
                        .value(bonus)
                        .event(userAddBonusMsgDTO.getEvent())
                        .createTime(new Date())
                        .description(userAddBonusMsgDTO.getDescription())
                        .build()
        );
        log.info(userAddBonusMsgDTO.getDescription());

    }

}
