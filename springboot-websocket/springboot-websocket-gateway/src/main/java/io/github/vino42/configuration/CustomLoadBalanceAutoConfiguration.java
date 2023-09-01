package io.github.vino42.configuration;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义负载均衡自动配置
 */
@LoadBalancerClients(defaultConfiguration = GatewayLoadBalancerConfiguration.class)
public class CustomLoadBalanceAutoConfiguration {

}