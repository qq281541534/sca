package com.theone.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * 自定义打印日志过滤器
 * Tips: 类名称必须遵循以下规则
 * 1.PreLog与application.yml中配置。 eg. - PreLog=a, b
 * 2.必须以GatewayFilterFactory结尾，否则无效
 *
 * @author liuyu
 */
@Component
@Slf4j
public class PreLogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config) {

        return ((exchange, chain) -> {
            // 必须放在这里，如果放在方法外启动时会打印多次
            log.info("自定义PreLogFilter中的参数是{}={}", config.getName(), config.getValue());
            // 修改request
            ServerHttpRequest modifiedRequest = exchange.getRequest()
                    .mutate()
                    .build();
            // 修改exchange
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();
            // 传递给下一个过滤器
            return chain.filter(modifiedExchange);
        });
    }
}
