package io.github.vino42.mapping;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.cloud.gateway.handler.FilteringWebHandler;
import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static io.github.vino42.constant.WebSocketConstants.ORIGIN_GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping.ManagementPortType.*;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * =====================================================================================
 *
 * @Created :   2021/12/19 22:30
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 自定义routeMapping 主要为了自定义loadbalancer服务
 * =====================================================================================
 */
public class CustomRoutePredicateHandlerMapping extends RoutePredicateHandlerMapping {
    private final FilteringWebHandler webHandler;

    private final RouteLocator routeLocator;

    private final Integer managementPort;

    private final ManagementPortType managementPortType;

    public CustomRoutePredicateHandlerMapping(FilteringWebHandler webHandler, RouteLocator routeLocator, GlobalCorsProperties globalCorsProperties, Environment environment) {
        super(webHandler, routeLocator, globalCorsProperties, environment);
        this.webHandler = webHandler;
        this.routeLocator = routeLocator;

        this.managementPort = getPortProperty(environment, "management.server.");
        this.managementPortType = getManagementPortType(environment);
        setOrder(1);
        setCorsConfigurations(globalCorsProperties.getCorsConfigurations());
    }

    private ManagementPortType getManagementPortType(Environment environment) {
        Integer serverPort = getPortProperty(environment, "server.");
        if (this.managementPort != null && this.managementPort < 0) {
            return DISABLED;
        }
        return ((this.managementPort == null || (serverPort == null && this.managementPort.equals(8080))
                || (this.managementPort != 0 && this.managementPort.equals(serverPort))) ? SAME : DIFFERENT);
    }

    private static Integer getPortProperty(Environment environment, String prefix) {
        return environment.getProperty(prefix + "port", Integer.class);
    }

    @Override
    protected Mono<?> getHandlerInternal(ServerWebExchange exchange) {
        return super.getHandlerInternal(exchange);
    }

    @Override
    protected CorsConfiguration getCorsConfiguration(Object handler, ServerWebExchange exchange) {
        return super.getCorsConfiguration(handler, exchange);
    }

    @Override
    protected Mono<Route> lookupRoute(ServerWebExchange exchange) {
        return this.routeLocator.getRoutes()
                // individually filter routes so that filterWhen error delaying is not a
                // problem
                .concatMap(route -> Mono.just(route).filterWhen(r -> {
                            // add the current route we are testing
                            exchange.getAttributes().put(GATEWAY_PREDICATE_ROUTE_ATTR, r.getId());
                            ServerHttpRequest req = exchange.getRequest();
                            exchange.getAttributes().put(ORIGIN_GATEWAY_REQUEST_URL_ATTR, req.getURI().getRawPath());

                            addOriginalRequestUrl(exchange, req.getURI());
                            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, req.getURI());
                            ServerWebExchange newExchange = exchange.mutate().request(req).build();

                            return r.getPredicate().apply(newExchange);
                        })
                        // instead of immediately stopping main flux due to error, log and
                        // swallow it
                        .doOnError(e -> logger.error("Error applying predicate for route: " + route.getId(), e))
                        .onErrorResume(e -> Mono.empty()))
                // .defaultIfEmpty() put a static Route not found
                // or .switchIfEmpty()
                // .switchIfEmpty(Mono.<Route>empty().log("noroute"))
                .next()
                // TODO: error handling
                .map(route -> {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Route matched: " + route.getId());
                    }
                    validateRoute(route, exchange);
                    return route;
                });

        /*
         * TODO: trace logging if (logger.isTraceEnabled()) {
         * logger.trace("RouteDefinition did not match: " + routeDefinition.getId()); }
         */
    }

    @Override
    protected void validateRoute(Route route, ServerWebExchange exchange) {
        super.validateRoute(route, exchange);
    }

    @Override
    protected String getSimpleName() {
        return super.getSimpleName();
    }
}
