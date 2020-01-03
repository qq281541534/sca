package com.theone.contentcenter.feignclient.fallback;

import com.theone.apimodel.dto.UserDTO;
import com.theone.contentcenter.feignclient.UserCenterFeignClient;
import org.springframework.stereotype.Component;

/**
 * 用户中心的feignClient请求触发所配置限流后，进行的处理（需要捕获到异常的请使用factory）
 *
 * @author liuyu
 */
@Component
public class UserCenterFeignClientFallback implements UserCenterFeignClient{

    @Override
    public UserDTO findById(Integer id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setWxNickname("默认用户");
        return userDTO;
    }
}
