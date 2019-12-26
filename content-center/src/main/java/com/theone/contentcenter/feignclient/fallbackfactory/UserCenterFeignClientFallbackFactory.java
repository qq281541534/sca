package com.theone.contentcenter.feignclient.fallbackfactory;

import com.theone.contentcenter.domain.dto.user.UserDTO;
import com.theone.contentcenter.feignclient.UserCenterFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户中心的feignClient请求触发所配置限流后，可以捕获异常进行处理（功能比fallback更强,可以处理异常）
 *
 * @author liuyu
 */
@Slf4j
@Component
public class UserCenterFeignClientFallbackFactory implements FallbackFactory<UserCenterFeignClient> {
    @Override
    public UserCenterFeignClient create(Throwable throwable) {

        return new UserCenterFeignClient() {
            @Override
            public UserDTO findById(Integer id) {
                log.warn("fegin的远程调用被限流或降级了");
                UserDTO userDTO = new UserDTO();
                userDTO.setWxNickname("默认用户");
                return userDTO;
            }
        };
    }
}
