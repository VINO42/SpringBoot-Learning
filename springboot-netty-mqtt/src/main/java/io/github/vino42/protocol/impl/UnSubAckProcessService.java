package io.github.vino42.protocol.impl;

import io.github.vino42.protocol.ProtocolProcessService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import org.springframework.stereotype.Service;

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
@Service("UNSUBACK")
public class UnSubAckProcessService implements ProtocolProcessService {
    @Override
    public void process(Channel channel, MqttMessage mqttUnsubAckMessage) {
        MqttUnsubAckMessage msg = (MqttUnsubAckMessage) mqttUnsubAckMessage;
        MqttUnsubAckMessage unsubAckMessage = (MqttUnsubAckMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.UNSUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(msg.variableHeader().messageId()),
                null);
        channel.writeAndFlush(unsubAckMessage);
    }
}
