package io.github.vino42.configuration;

import io.github.vino42.mapping.CustomRoutePredicateHandlerMapping;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.cloud.gateway.handler.FilteringWebHandler;
import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/2 6:02
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 网关routMapping配置
 * =====================================================================================
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(GatewayAutoConfiguration.class)
public class CustomGatewayConfiguration {
    @Bean
    public RoutePredicateHandlerMapping customRoutePredicateHandlerMapping(FilteringWebHandler webHandler, RouteLocator routeLocator, GlobalCorsProperties globalCorsProperties, Environment environment) {
        return new CustomRoutePredicateHandlerMapping(webHandler, routeLocator, globalCorsProperties, environment);
    }
}
