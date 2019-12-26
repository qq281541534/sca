package com.theone.gateway;

import lombok.Data;

import java.time.LocalTime;

/**
 * 谓词工厂配置类
 * 用于application.yml中spring.cloud.gateway.routes.predicates中的属性
 *
 * @author liuyu
 */
@Data
public class TimeBetweenConfig {

    private LocalTime start;
    private LocalTime end;
}
