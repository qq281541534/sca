package com.theone.contentcenter.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.theone.apimodel.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * 这对@SentinelResource注解的限流、降级异常处理
 *
 * @author liuyu
 */
@Slf4j
public class SentinelDemoControllerBlockHandler {

    /**
     * 限流或者降级
     * PS：和被保护方法必须有相同的请求参数和返回值类型
     *
     * @param a 被保护方法的参数
     * @param e 限流的异常
     * @return
     */
    public static String block(String a, BlockException e){
        log.warn("限流或降级了 block", e);
        return "限流或降级了 block";

    }

    /**
     * 用于restTemplate demo的限流或降级的处理
     * 正常情况这里返回的应该是一个通用的ResponseData的消息体，这里new 一个新的UserDTO做例子
     *
     * @param userId
     * @param e
     * @return
     */
    public static UserDTO blockRestTemplate(String userId, BlockException e){
        log.warn("限流或降级了 blockRestTemplate", e);
        return new UserDTO();
    }
}
