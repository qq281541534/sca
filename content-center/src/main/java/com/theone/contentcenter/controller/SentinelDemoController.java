package com.theone.contentcenter.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.nacos.client.utils.StringUtils;
import com.theone.contentcenter.domain.dto.user.UserDTO;
import com.theone.contentcenter.sentinel.SentinelDemoControllerBlockHandler;
import com.theone.contentcenter.sentinel.SentinelDemoControllerFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * sentinel 测试，需要配合sentinelDashboard一起测试
 * PS：配置文件需要设置如下参数：
 *       sentinel:
 #        filter:
 #          # 关闭掉对Spring MVC端点的保护（测试使用）
 #          enabled: false
 *
 * @author liuyu
 */
@Slf4j
@RestController
public class SentinelDemoController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 基于restTemplate整合sentinel的示例接口
     *
     * @param userId
     * @return
     */
    @GetMapping("/sentinel-rest-template/{userId}")
    public UserDTO testSentinelRestTemplate(@PathVariable String userId){
        return this.restTemplate.getForObject(
                "http://user-center/users/{id}",
                UserDTO.class,
                userId
        );
    }

    /**
     * sentinelResource注解示例（推荐使用）
     * 目的：
     * 1.当业务逻辑中抛出了异常，则会调用fallbackClass中的fallback方法进行处理，并且会调用dashboard中的降级规则
     * 2.当业务并发量超过dashboard中配置的流控规则时，则会触发blockHandlerClass中的block方法
     *
     * @param a
     * @return
     */
    @GetMapping("/test-sentinel-resource")
    @SentinelResource(
            value = "sentinel-resource",
            blockHandlerClass = SentinelDemoControllerBlockHandler.class,
            blockHandler = "block",
            fallbackClass = SentinelDemoControllerFallback.class,
            fallback = "fallback"
    )
    public String testSentinelResource(@RequestParam(required = false) String a){

        if(StringUtils.isBlank(a)){
            throw new IllegalArgumentException("参数异常");
        }
        return "成功";
    }

    /**
     * 测试使用sentinel api（编码形式，不推荐）
     * ContextUtil、SphU、Tracer
     *
     * @param a
     * @return
     */
    @GetMapping("/test-sentinel-api")
    public String sentinelApi(@RequestParam(required = false) String a){
        String resourceName = "test-sentinel";
        // 对应流控规则中的针对来源，来源为test-wfw的限流
        ContextUtil.enter(resourceName, "test-wfw");

        // 定义sentinel保护的资源，名称为test-sentinel-api
        Entry entry = null;
        try {
            entry = SphU.entry(resourceName);

            // 需要保护的业务逻辑
            if (StringUtils.isBlank(a)) {
                throw new IllegalArgumentException("参数异常");
            }
            return "成功";
        // 如果被限流或降级了会抛出BlockException
        } catch (BlockException e) {
            log.error("被限流了");
            return "被限流了";
        }
        // 这里单独对业务异常进行捕获，然后调用Tracer计数才会算为限流的次数
        catch (IllegalArgumentException e1) {
            // 统计IllegalArgumentException 发生次数、发生占比...
            Tracer.trace(e1);
            return "参数非法";
        }
        finally {
            if (entry != null) {
                // 退出
                entry.exit();
            }
            ContextUtil.exit();
        }
    }



}
