package com.theone.gateway;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 编写自定义的路由谓词工厂
 * Tips: 类名称必须遵循以下规则
 * 1.TimeBetween与application.yml中配置。 eg. - TimeBetween=上午12:10,下午4:00
 * 2.必须以RoutePredicateFactory结尾，否则无效
 *
 * @author liuyu
 */
@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeBetweenConfig> {

    public TimeBetweenRoutePredicateFactory() {
        super(TimeBetweenConfig.class);
    }

    /**
     * 控制路由的条件
     *
     * @param config
     * @return
     */
    @Override
    public Predicate<ServerWebExchange> apply(TimeBetweenConfig config) {
        LocalTime start = config.getStart();
        LocalTime end = config.getEnd();
        return serverWebExchange -> {
            LocalTime now = LocalTime.now();
            return now.isAfter(start) && now.isBefore(end);
        };
    }

    /**
     * 指定配置配置类中属性的顺序
     * 例：spring.cloud.gateway.routes.predicates={start},{end}
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("start", "end");
    }
}
