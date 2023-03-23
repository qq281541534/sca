package com.theone.contentcenter.feignclient.interceptor;

import com.alibaba.nacos.client.utils.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用feign传递token的拦截器
 * Tips:
 * 这里有个注意点：
 * 1.如果作用全局过滤器，需要加上@Componet就可以了，不需要在其他地方引用
 * 2.如果作用于某个微服务的Feign client，则不加@Componet，而是在
 *   a. application.yml中加上对应的配置：
 *      feign.client.config.{feignClientName}.requestInterceptors=com.theone.contentcenter.feignclient.interceptor.TokenRelayRequestInterceptor
 *   b.在对应的@FeignClient类的注解中加入configuration
 *
 * @author liuyu
 */
@Component
public class TokenRelayRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 1. 获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("X-Token");

        // 2. 传递token
        if(StringUtils.isNotBlank(token)){
            requestTemplate.header("X-Token", token);
        }
    }
}
