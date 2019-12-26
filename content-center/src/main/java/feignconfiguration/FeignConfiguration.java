package feignconfiguration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign自定的配置文件
 * PS: 该类不能放在com.theone.contentcenter下，如果放在com.theone.contentcenter中就会有父子
 * 上下文重叠的问题！最终的结果就是该细粒度的配置变成的全局配置，调用user-center和其他微服务都会使用同一规则
 *
 * @author liuyu
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public Logger.Level level(){
        // 打印所有请求细节
        return Logger.Level.FULL;
    }
}
