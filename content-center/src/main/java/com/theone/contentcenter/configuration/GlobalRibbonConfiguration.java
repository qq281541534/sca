package com.theone.contentcenter.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import ribbonconfiguration.RibbonConfiguration;

/**
 * 全局的客户端负载均衡配置
 *
 * @author liuyu
 */
@Configuration
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class GlobalRibbonConfiguration {

}
