package io.github.vino42.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.ConsistentHash;
import cn.hutool.json.JSONUtil;
import io.github.vino42.constant.ServiceStatusEnum;
import io.github.vino42.hashring.HashRingNode;
import io.github.vino42.hashring.KafkaServiceMessage;
import io.github.vino42.hashring.MyConsistentHash;
import io.github.vino42.service.KafkaPublishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.vino42.constant.WebSocketConstants.HASH_RING_KEY;
import static io.github.vino42.constant.WebSocketConstants.USER_ID;


/**
 * =====================================================================================
 *
 * @Created :   2023/8/26 1:22
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription : kafka 消费者 在网关处作用为监听后端服务实例上下线的动作 进行一系列操作，这里其实可以用nacos进行监听处理
 * =====================================================================================
 */
@Component
@Slf4j
public class HashRingKafkaListener {
    @Autowired
    ReactiveStringRedisTemplate redisTemplate;

    @Autowired
    MyConsistentHash<HashRingNode> consistentHash;
    @Autowired
    KafkaPublishService kafkaPublishService;

    /**
     * 当websocket实例上下线重载hashring
     *
     * @param record
     * @param acknowledgment
     */
    @KafkaListener(topics = "service_up_down")
    public void listenUser(ConsumerRecord<?, String> record, Acknowledgment acknowledgment) {
        try {
            String key = String.valueOf(record.key());
            String body = record.value();
            log.info("[KAFKA consume message, topic:{}, message:{}]", "service_up_down", body);

            KafkaServiceMessage upDownNode = JSONUtil.toBean(body, KafkaServiceMessage.class);

            //下线 直接就下
            if (ServiceStatusEnum.DOWN.getType().equals(upDownNode.getServiceStatus())) {
                HashRingNode downNode = new HashRingNode(upDownNode.getServiceIp());

                consistentHash.remove(downNode);
            }
            //服务上线
            if (ServiceStatusEnum.UP.getType().equals(upDownNode.getServiceStatus())) {
                //添加前的老的环上的数据集合
                Map<String, HashRingNode> oldUserIdAndService = new ConcurrentHashMap<>();

                //拿到所有的用户id列表集合 该hash结构为userid-hash
                Map<Object, Object> userIdAndHashInRedis = redisTemplate.opsForHash().entries(USER_ID).collectMap(Map.Entry::getKey, Map.Entry::getValue).block();
                for (Object keyId : userIdAndHashInRedis.keySet()) {
                    String userId = (String) keyId;
                    HashRingNode oldHashRingNode = consistentHash.get(userId);
                    if (null != oldHashRingNode) {
                        oldUserIdAndService.put(userId, oldHashRingNode);
                    }
                }
                //往环中加节点
                //一些客户端可能需要进行重连接 这里用列表记录下
                List<String> userIdClientsToReset = new ArrayList<>();

                HashRingNode serviceNode = new HashRingNode(upDownNode.getServiceIp());
                consistentHash.add(serviceNode);
                //添加新的节点后重排原有的哈希环。原来的哈希环的插槽要变动
                for (Map.Entry<String, HashRingNode> entry : oldUserIdAndService.entrySet()) {
                    HashRingNode newServiceNode = entry.getValue();
                    log.debug("【遍历】当前客户端 [{}] 的新节点 [{}]", entry.getKey(), newServiceNode);
                    // 同一 userId 路由到的真实服务节点前后可能会不一样, 把这些 userId 筛选出来
                    if (!newServiceNode.getKey().equals(entry.getValue().getKey())) {
                        userIdClientsToReset.add(entry.getKey());
                        log.info("【哈希环更新】客户端在哈希环的映射服务节点发生了变动: [{}]: [{}] -> [{}]", entry.getKey(), entry.getValue(), newServiceNode);
                    }
                    //发送kafka进行消息通知 方便响应客户端进行重连接
                }
                if (CollUtil.isNotEmpty(userIdClientsToReset)) {
                    kafkaPublishService.send("hashring_change", JSONUtil.toJsonStr(userIdClientsToReset));
                }
            }
            redisTemplate.opsForHash().putAll(HASH_RING_KEY, consistentHash.getCircle());
            log.info("【哈希环】实例上线之后为 {}", JSONUtil.toJsonStr(consistentHash));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //手动ack
            acknowledgment.acknowledge();
        }
    }
}
