package com.theone.contentcenter.feignclient;

import com.theone.apimodel.dto.UserDTO;
import com.theone.apimodel.service.UserService;
import com.theone.contentcenter.feignclient.fallbackfactory.UserCenterFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
public interface UserCenterFeignClient extends UserService{

    /**
     * http://user-center/users/{id}
     *
     * @param id
     * @return
     */
    @Override
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable("id") Integer id);
}
