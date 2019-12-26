package ribbonconfiguration;

import com.netflix.loadbalancer.IRule;
import com.theone.contentcenter.ribbon.NacosFinalRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon自定的配置文件
 * PS: 该类不能放在com.theone.contentcenter下，如果放在com.theone.contentcenter中就会有父子
 * 上下文重叠的问题！最终的结果就是该细粒度的配置变成的全局配置，调用user-center和其他微服务都会使用同一规则
 *
 * @author liuyu
 */
@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule(){
//        return new RandomRule();
//        return new NacosWeightedRibbonRule();
//        return new NacosRule();
        return new NacosFinalRule();
    }
}
