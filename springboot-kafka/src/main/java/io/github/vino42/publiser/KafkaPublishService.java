package io.github.vino42.publiser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        kafkaTemplate.send(topic, json);
    }
}
