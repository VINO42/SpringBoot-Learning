package io.github.vino42.consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


/**
 * =====================================================================================
 *
 * @Created :   2023/8/26 1:22
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription : kafka 消费者
 * =====================================================================================
 */
@Component
@Slf4j
public class CustomKafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(topics = "test")
    public void listenUser(ConsumerRecord<?, String> record, Acknowledgment acknowledgment) {
        try {
            String key = String.valueOf(record.key());
            String body = record.value();
            log.info("\n=====\ntopic:test，key{}，message:{}\n=====\n", key, body);
            log.info("\n=====\ntopic:test，key{}，payLoadJson:{}\n=====\n", key, body);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //手动ack
            acknowledgment.acknowledge();
        }
    }
}
