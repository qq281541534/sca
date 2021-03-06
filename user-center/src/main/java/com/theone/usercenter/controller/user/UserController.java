package com.theone.usercenter.controller.user;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.theone.apimodel.dto.UserDTO;
import com.theone.apimodel.http.UserServiceApi;
import com.theone.common.auth.CheckLogin;
import com.theone.common.utils.JwtOperator;
import com.theone.usercenter.domain.dto.user.JwtTokenRespDTO;
import com.theone.usercenter.domain.dto.user.LoginRespDTO;
import com.theone.usercenter.domain.dto.user.UserLoginDTO;
import com.theone.usercenter.domain.dto.user.UserRespDTO;
import com.theone.usercenter.domain.entity.user.User;
import com.theone.usercenter.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyu
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserController implements UserServiceApi {
    private final UserService userService;
    private final WxMaService wxMaService;
    private final JwtOperator jwtOperator;

    @Override
    @GetMapping("/{id}")
    @CheckLogin
    public UserDTO findById(@PathVariable Integer id) {
        log.info("我被请求了...");
        return this.userService.findById(id);
    }

    /**
     * 模拟生成token(假的登录)
     */
    @GetMapping("/gen-token")
    public String genToken() {
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", 1);
        userInfo.put("wxNickname", "大目");
        userInfo.put("role", "admin");
        return this.jwtOperator.generateToken(userInfo);
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) throws WxErrorException {
        // 微信小程序服务端校验是否已经登录的结果
        WxMaJscode2SessionResult result = this.wxMaService.getUserService()
            .getSessionInfo(loginDTO.getCode());

        // 微信的openId，用户在微信这边的唯一标示
        String openid = result.getOpenid();

        // 看用户是否注册，如果没有注册就（插入）
        // 如果已经注册
        User user = this.userService.login(loginDTO, openid);

        // 颁发token
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", user.getId());
        userInfo.put("wxNickname", user.getWxNickname());
        userInfo.put("role", user.getRoles());

        String token = jwtOperator.generateToken(userInfo);

        log.info(
            "用户{}登录成功，生成的token = {}, 有效期到:{}",
            loginDTO.getWxNickName(),
            token,
            jwtOperator.getExpirationTime()
        );

        // 构建响应
        return LoginRespDTO.builder()
            .user(
                UserRespDTO.builder()
                    .id(user.getId())
                    .avatarUrl(user.getAvatarUrl())
                    .bonus(user.getBonus())
                    .wxNickname(user.getWxNickname())
                    .build()
            )
            .token(
                JwtTokenRespDTO.builder()
                    .expirationTime(jwtOperator.getExpirationTime().getTime())
                    .token(token)
                    .build()
            )
            .build();
    }
}
