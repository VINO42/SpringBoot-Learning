package io.github.vino42.listener;

import cn.hutool.json.JSONUtil;
import io.github.vino42.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * kafka监听器 这里主要是为了消费 网关推送过来的哈希环变更事件，为了节点迁移后主动让客户端重连
 */
@Component
@Slf4j
public class HashRingKafkaListener {
    @Autowired
    WebSocketService webSocketService;

    /**
     * 接收hash环变更操作 让一部分变动的客户端下线
     *
     * @param record
     * @param acknowledgment
     */
    @KafkaListener(topics = "hashring_change")
    public void listenUser(ConsumerRecord<?, String> record, Acknowledgment acknowledgment) {
        try {
            String body = record.value();
            log.info("[KAFKA consume message, topic:{}, message:{}]", "service_up_down", body);

            List<String> clientsNeedReconect = JSONUtil.toList(body, String.class);
            webSocketService.disconnectSomeClientByServer(clientsNeedReconect);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //手动ack
            acknowledgment.acknowledge();
        }
    }
}