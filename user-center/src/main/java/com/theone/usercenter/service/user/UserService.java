package com.theone.usercenter.service.user;

import com.theone.usercenter.dao.bouns.BounsEventLogMapper;
import com.theone.usercenter.dao.user.UserMapper;
import com.theone.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.theone.usercenter.domain.dto.user.UserLoginDTO;
import com.theone.usercenter.domain.entity.bouns.BounsEventLog;
import com.theone.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author liuyu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final BounsEventLogMapper bounsEventLogMapper;

    public User findById(Integer id){
        return this.userMapper.selectByPrimaryKey(id);
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
                      .wxNickname(loginDTO.getNickName())
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
