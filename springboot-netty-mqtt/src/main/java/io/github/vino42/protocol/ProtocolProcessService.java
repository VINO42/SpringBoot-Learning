package io.github.vino42.protocol;

import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/28 22:56
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription : 控制报文处理接口
 * M
 * =====================================================================================
 */
public interface ProtocolProcessService {
    /**
     * @param channel
     * @param msg
     * @see MqttMessageType
     */
    void process(Channel channel, MqttMessage msg);

}
