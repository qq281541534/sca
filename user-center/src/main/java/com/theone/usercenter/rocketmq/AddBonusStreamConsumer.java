package com.theone.usercenter.rocketmq;

import com.theone.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.theone.usercenter.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * 添加积分的消息消费者
 *
 * @author liuyu
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AddBonusStreamConsumer {

    private final UserService userService;

    @StreamListener(Sink.INPUT)
    public void receive(UserAddBonusMsgDTO message) {
        userService.addBonus(message);
    }
}
