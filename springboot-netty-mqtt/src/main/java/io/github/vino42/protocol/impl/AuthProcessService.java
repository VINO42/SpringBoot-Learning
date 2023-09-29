package io.github.vino42.protocol.impl;

import io.github.vino42.protocol.ProtocolProcessService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttMessage;
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
@Service("AUTH")
public class AuthProcessService implements ProtocolProcessService {
    @Override
    public void process(Channel channel, MqttMessage msg) {

    }
}
