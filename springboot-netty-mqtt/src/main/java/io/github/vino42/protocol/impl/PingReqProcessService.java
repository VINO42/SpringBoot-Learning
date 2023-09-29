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
@Service("PINGREQ")
public class PingReqProcessService implements ProtocolProcessService {
    @Override
    public void process(Channel channel, MqttMessage msg) {
        MqttMessage pingRespMessage = MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PINGRESP, false, MqttQoS.AT_MOST_ONCE, false, 0),
                null,
                null);
        channel.writeAndFlush(pingRespMessage);
    }
}
