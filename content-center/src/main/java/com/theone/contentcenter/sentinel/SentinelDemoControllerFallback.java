package com.theone.contentcenter.sentinel;

import com.theone.apimodel.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * 针对@SentinelResource注解的降级时的异常处理
 *
 * @author liuyu
 */
@Slf4j
public class SentinelDemoControllerFallback {

    /**
     * 当调用的端点发生异常时会调用该方法
     * 和被保护方法必须有相同的请求参数和返回值类型
     *
     * @param a 被保护方法的参数
     * @param throwable 业务端点抛出的异常
     * @return
     */
    public static String fallback(String a, Throwable throwable){
        log.warn("降级了 fallback", throwable);
        return "降级了 fallback";
    }

    /**
     * 用于sentinelRestTemplate demo的异常处理
     * 正常情况这里返回的应该是一个通用的ResponseData的消息体，这里new 一个新的UserDTO做例子
     *
     * @param userId 被保护方法的参数
     * @param e 业务端点抛出的异常
     * @return
     */
    public static UserDTO fallbackRestTemplate(String userId, Throwable e){
        log.warn("降级了 blockRestTemplate", e);
        return new UserDTO();
    }
}
