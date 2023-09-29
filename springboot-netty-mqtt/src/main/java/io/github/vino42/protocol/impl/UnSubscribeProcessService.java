package io.github.vino42.protocol.impl;

import io.github.vino42.protocol.ProtocolProcessService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/28 23:13
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Service("UNSUBSCRIBE")
public class UnSubscribeProcessService implements ProtocolProcessService {
    @Override
    public void process(Channel channel, MqttMessage mqttUnsubscribeMessage) {
        MqttUnsubscribeMessage msg = (MqttUnsubscribeMessage) mqttUnsubscribeMessage;
        List<String> topicFilters = msg.payload().topics();
        String clinetId = (String) channel.attr(AttributeKey.valueOf("clientId")).get();
//        topicFilters.forEach(topicFilter -> {
//            grozaSubscribeStoreService.remove(topicFilter, clinetId);
//            log.info("UNSUBSCRIBE - clientId: {}, topicFilter: {}", clinetId, topicFilter);
//        });
        MqttUnsubAckMessage unsubAckMessage = (MqttUnsubAckMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.UNSUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(msg.variableHeader().messageId()),
                null);
        channel.writeAndFlush(unsubAckMessage);
    }
}
