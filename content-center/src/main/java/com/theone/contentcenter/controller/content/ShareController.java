package com.theone.contentcenter.controller.content;

import com.github.pagehelper.PageInfo;
import com.theone.common.auth.CheckLogin;
import com.theone.common.utils.JwtOperator;
import com.theone.contentcenter.domain.dto.content.ShareDTO;
import com.theone.contentcenter.domain.entity.content.Share;
import com.theone.contentcenter.service.content.ShareService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuyu
 */
@RestController
@RequestMapping("/shares")
@RequiredArgsConstructor
@RefreshScope
public class ShareController {

    private final ShareService shareService;
    private final JwtOperator jwtOperator;

    /**
     * 通过ID查询分享
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @CheckLogin
    public ShareDTO findById(@PathVariable("id") Integer id){
        return shareService.findById(id);
    }

    /**
     * 查询列表
     *
     * @param title
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/q")
    public PageInfo<Share> query(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestHeader(required = false, value = "X-Token") String token){
        if(pageSize > 100){
            pageSize = 100;
        }
        Integer userId = null;
        if (StringUtils.isNotBlank(token)) {
            Claims claimsFromToken = jwtOperator.getClaimsFromToken(token);
            userId = (Integer) claimsFromToken.get("id");
        }

        return shareService.query(title, pageNo, pageSize, userId);
    }

    @GetMapping("/exchange/{id}")
    @CheckLogin
    public Share exchangeById(@PathVariable("id") String id, HttpServletRequest request){
        return shareService.exchangeById(id, request);
    }

    @Value("${my.configuration:test1}")
    private String myConfiguration;

    @GetMapping("/test-config")
    public String testConfig(){
        return myConfiguration;
    }
}
