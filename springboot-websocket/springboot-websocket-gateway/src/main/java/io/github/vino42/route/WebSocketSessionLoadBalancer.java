package io.github.vino42.route;

import cn.hutool.core.util.StrUtil;
import io.github.vino42.hashring.HashRingNode;
import io.github.vino42.hashring.MyConsistentHash;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.vino42.constant.WebSocketConstants.WEB_SOCKET_SERVICE_NAME;

/**
 * WebSocket 负载均衡器
 */
@Slf4j
public class WebSocketSessionLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketSessionLoadBalancer.class);

    final MyConsistentHash<HashRingNode> consistentHashRouter;
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;


    public WebSocketSessionLoadBalancer(MyConsistentHash<HashRingNode> consistentHashRouter, ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this.consistentHashRouter = consistentHashRouter;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        RequestDataContext context = (RequestDataContext) request.getContext();
        List<String> strings = context.getClientRequest().getHeaders().get("serviceId");
        String instancesId = strings.get(0);

        if (WEB_SOCKET_SERVICE_NAME.equals(instancesId)) {
            // 获取需要参与哈希的字段，此项目为 userId
            final String userIdFromRequest = getUserIdFromRequest(context);
            if (context != null) {
                if (this.serviceInstanceListSupplierProvider != null) {
                    ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
                    return ((Flux) supplier.get()).next().map(list -> getInstanceResponse((List<ServiceInstance>) list, instancesId, userIdFromRequest));
                }
            }

        }
        return choose();
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, String instancesId, String userIdFromRequest) {
        List<ServiceInstance> filteredInstance = instances.stream().filter(d -> WEB_SOCKET_SERVICE_NAME.equalsIgnoreCase(d.getServiceId())).collect(Collectors.toList());

        if (filteredInstance.isEmpty()) {
            return getServiceInstanceEmptyResponse();
        } else {
            return getServiceInstanceResponseByUserId(instances, userIdFromRequest);
        }
    }


    private Response<ServiceInstance> getServiceInstanceEmptyResponse() {
        log.warn("No servers available for service：");
        return new EmptyResponse();
    }

    @Override
    public Mono<Response<ServiceInstance>> choose() {
        RoundRobinLoadBalancer roundRobinLoadBalancer = new RoundRobinLoadBalancer(serviceInstanceListSupplierProvider, WEB_SOCKET_SERVICE_NAME);
        return roundRobinLoadBalancer.choose();
    }


    private Response<ServiceInstance> getServiceInstanceResponseByUserId(List<ServiceInstance> instances, String userIdFromRequest) {
        if (StrUtil.isBlankOrUndefined(userIdFromRequest)) {
            return choose().block();
        }
        HashRingNode hashRingNode = consistentHashRouter.get(userIdFromRequest);
        if (null != hashRingNode) {
            // 获取当前注册中心的实例
            for (ServiceInstance instance : instances) {
                // 如果 userId 映射后的真实节点的 IP 与某个实例 IP 一致，就转发
                if (instance.getHost().equals(hashRingNode.getKey())) {
                    logger.debug("当前客户端[{}]匹配到真实节点 {}", userIdFromRequest, hashRingNode.getKey());
                    return new DefaultResponse(instance);
                }
            }
        }
        log.warn("No servers available for websocket service, try to get first , mabe null");
        return new DefaultResponse(instances.get(0));
    }

    /**
     * 从 WS/HTTP 请求 中获取待哈希字段 userId
     *
     * @param context 请求上下文
     * @return userId，可能为空
     */
    protected static String getUserIdFromRequest(RequestDataContext context) {
        URI originalUrl = (URI) context.getClientRequest().getUrl();
        String userId = null;
        if (originalUrl.getPath().startsWith("/ws")) {
            // ws: "lb://websocket-server/connect/1" 获取这里面的最后一个路径参数 userId: 1
            String elements = context.getClientRequest().getUrl().getPath();
            String[] split = elements.split("\\/");
            userId = split[split.length - 1];

//            PathContainer.Element lastElement = elements.get(elements.size() - 1);
//            userId = lastElement.value();
            logger.debug("【网关负载均衡】WebSocket 获取到 userId: {}", userId);
        } else {
            // 前提：websocket http 服务 userId 放在 query 中
            // rest: "lb://websocket-server/send?userId=1&message=text"
            HttpHeaders headers = context.getClientRequest().getHeaders();
            List<String> userIds = headers.get("userId");
            if (null != userIds && !userIds.isEmpty()) {
                userId = userIds.get(0);
                logger.debug("【网关负载均衡】HTTP 获取到 userId: {}", userId);
            }
        }
        return userId;
    }

}