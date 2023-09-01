package io.github.vino42.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/30 21:29
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription : kafak 消息生产者
 * =====================================================================================
 */
@Slf4j
@Component
public class KafkaPublishService {
    @Autowired
    KafkaTemplate kafkaTemplate;

    /**
     * 这里为了简单 直接发送json字符串
     *
     * @param json
     */
    public void send(String topic, String json) {
        log.info("[KAFKA send message, topic:{}, message:{}]", topic, json);
        kafkaTemplate.send(topic, json);
    }
}
