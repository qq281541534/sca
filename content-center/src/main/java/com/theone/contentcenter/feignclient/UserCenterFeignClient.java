package com.theone.contentcenter.feignclient;

import com.theone.apimodel.http.UserServiceApi;
import com.theone.contentcenter.feignclient.fallbackfactory.UserCenterFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * user-center feign client
 * name: 指定该feignclient请求的是哪个微服务
 * fallback: 指定被降级或限流后的处理
 * fallbackFactory: 与fallback的不同点在于，可以收到异常，进行异常处理
 *
 * @author liuyu
 */
@FeignClient(
        name = "user-center",
//        fallback = UserCenterFeignClientFallback.class
        fallbackFactory = UserCenterFeignClientFallbackFactory.class//,
//        configuration = TokenRelayRequestInterceptor.class
)
public interface UserCenterFeignClient extends UserServiceApi {

}
