package io.github.vino42.configuration;

import io.github.vino42.hashring.MyConsistentHash;
import io.github.vino42.route.WebSocketSessionLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/2 5:19
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 网关loadbalancer定义
 * =====================================================================================
 */
@Configuration(proxyBeanMethods = false)
public class GatewayLoadBalancerConfiguration {
    @Bean
    public ServiceInstanceListSupplier customServiceInstanceListSupplier(ConfigurableApplicationContext context) {
        return ServiceInstanceListSupplier.builder().withDiscoveryClient().withWeighted().withCaching()
                .build(context);
    }


    @Bean
    public ReactorServiceInstanceLoadBalancer tenantAndGrayServiceInstanceLoadBalancer(Environment environment, MyConsistentHash consistentHash, LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new WebSocketSessionLoadBalancer(consistentHash, loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }
}
