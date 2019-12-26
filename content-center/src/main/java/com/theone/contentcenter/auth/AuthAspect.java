package com.theone.contentcenter.auth;

import com.alibaba.nacos.client.utils.StringUtils;
import com.theone.contentcenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * AOP方式认证用户
 *
 * @author liuyu
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private final JwtOperator jwtOperator;

    /**
     * 验证登陆
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.theone.contentcenter.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        checkTokenAndGetRole();
        return point.proceed();
    }

    /**
     * 验证权限
     *
     * @param point
     */
    @Around("@annotation(com.theone.contentcenter.auth.CheckAuthorization)")
    public void checkAuthorization(ProceedingJoinPoint point) throws Throwable {
        try {
            // 1.效验token是否合法,并且换取用户角色
            String role = this.checkTokenAndGetRole();
            // 2.通过反射回去注解中所需的角色
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);
            String value = annotation.value();

            // 4.对比
            if (!StringUtils.equals(role, value)) {
                throw new SecurityException("用户无权访问");
            }
        } catch (Throwable throwable) {
            throw new SecurityException("用户无权访问", throwable);
        }
        point.proceed();
    }

    private String checkTokenAndGetRole() {
        try {
            // 1.从request中获取token
            HttpServletRequest request = getHttpRequest();
            String token = request.getHeader("X-Token");

            // 2.效验token是否合法&过期；如果不合法或过期抛出异常，否则放行
            if (!this.jwtOperator.validateToken(token)) {
                throw new SecurityException("Token不合法！");
            }

            // 3.效验成功将用户信息放入request的attribute中，用于其他地方验证访问资源
            Claims claims = this.jwtOperator.getClaimsFromToken(token);
            String role = (String) claims.get("role");
            request.setAttribute("id", claims.get("id"));
            request.setAttribute("wxNickname", claims.get("wxNickname"));
            request.setAttribute("role", role);
            return role;
        } catch (Throwable e) {
            throw new SecurityException("Token不合法！");
        }
    }

    private HttpServletRequest getHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        return attributes.getRequest();
    }


}
