package io.github.vino42.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;


/**
 * =====================================================================================
 *
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription : 全局过滤器 做一些简单的处理
 * =====================================================================================
 */
@Slf4j
@Component
public class CustomGlobalFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        ServerHttpRequest request = exchange.getRequest();
        String serviceId = (String) exchange.getAttributes().getOrDefault(GATEWAY_PREDICATE_MATCHED_PATH_ROUTE_ID_ATTR, "");
        if (StrUtil.isNotBlank(serviceId)) {
            request = request.mutate().header("serviceId", serviceId).build();

            if (queryParams.containsKey("userId")) {
                List<String> strings = queryParams.get("userId");
                String[] array = strings.toArray(new String[strings.size()]);

                request = request.mutate().header("userId", array).build();
            }
            return chain.filter(exchange.mutate().request(request).build());
        }
        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, exchange.getRequest().getURI());

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }


}
