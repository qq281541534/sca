package com.theone.contentcenter.ribbon;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 扩展基于Nacos server权重的负载均衡算法
 *
 * @author liuyu
 */
public class NacosWeightedRibbonRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        try {
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            // 获取请求微服务的名称
            String balancerName = loadBalancer.getName();

            // 拿到服务发现相关的API
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

            // 通过nacos server的基于权重的负载均衡算法 选取一个可用实例
            Instance instance = namingService.selectOneHealthyInstance(balancerName);

            return new NacosServer(instance);
        } catch (NacosException e) {
            return null;
        }
    }
}
