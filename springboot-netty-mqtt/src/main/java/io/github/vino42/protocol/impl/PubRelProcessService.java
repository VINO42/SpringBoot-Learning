package io.github.vino42.protocol.impl;

import io.github.vino42.protocol.ProtocolProcessService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
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
@Service("PUBREL")
@Slf4j
public class PubRelProcessService implements ProtocolProcessService {
    @Override
    public void process(Channel channel, MqttMessage msg) {
        MqttMessageIdVariableHeader variableHeader = (MqttMessageIdVariableHeader) msg.variableHeader();

        MqttMessage pubCompMessage = MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBCOMP, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(variableHeader.messageId()),
                null);
        log.info("PUBREL - clientId: {}, messageId: {}", channel.attr(AttributeKey.valueOf("clientId")).get(), variableHeader.messageId());
        channel.writeAndFlush(pubCompMessage);
    }
}
