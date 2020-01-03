package com.theone.contentcenter.sentinel;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.nacos.client.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 针对本微服务的消费者进行来源过滤
 * 使用：
 * 1.用于sentinel dashboard中簇点链路->流控规则->针对来源
 * 2.用于sentinel dashboard中簇点链路->授权规则->流控应用
 *
 * @author liuyu
 */
@Component
public class MyRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        String origin = request.getHeader("origin");
        if(StringUtils.isBlank(origin)){
            throw new IllegalArgumentException("origin must be specified.");
        }
        return origin;
    }
}
