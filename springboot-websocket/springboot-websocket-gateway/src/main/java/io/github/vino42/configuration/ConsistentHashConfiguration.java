package io.github.vino42.configuration;

import io.github.vino42.hashring.HashRingNode;
import io.github.vino42.hashring.MyConsistentHash;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.vino42.constant.WebSocketConstants.HASH_RING_KEY;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/2 1:18
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 一致性hash配置
 * =====================================================================================
 */
@Configuration(proxyBeanMethods = false)
public class ConsistentHashConfiguration {

    /**
     * 一致性hash 初始化
     *
     * @param
     * @return
     */
    @Bean
    public MyConsistentHash consistentHash(ReactiveStringRedisTemplate reactiveRedisTemplate) {
        final Map<Object, Object> hashRing = reactiveRedisTemplate.opsForHash().entries(HASH_RING_KEY)
                .collectMap(Map.Entry::getKey, Map.Entry::getValue).block();
        // 获取环中的所有真实节点
        List<HashRingNode> nodes = new ArrayList<>();
        for (Object key : hashRing.keySet()) {
            Long hashKey = (Long) key;
            HashRingNode ringNode = (HashRingNode) hashRing.get(hashKey);
            nodes.add(ringNode);
        }
        //默认给三个副本
        MyConsistentHash<HashRingNode> consistentHash = new MyConsistentHash<>(3, nodes);
        return consistentHash;
    }
}
