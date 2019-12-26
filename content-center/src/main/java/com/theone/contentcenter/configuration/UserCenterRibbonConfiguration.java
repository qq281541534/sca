package com.theone.contentcenter.configuration;

/**
 * 专用于user-center的负载均衡器
 * PS：共有两种细粒度的配置方式
 * 1.该形式为代码配置方式
 * 2.通过配置文件配置：user-center:ribbon:NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
 *
 * @author liuyu
 */
//@Configuration
//@RibbonClient(name = "user-center", configuration = RibbonConfiguration.class)
public class UserCenterRibbonConfiguration {

}
