package io.github.vino42.protocol.impl;

import cn.hutool.core.util.StrUtil;
import io.github.vino42.protocol.ProtocolProcessService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.CharsetUtil;
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
@Slf4j
@Service("CONNECT")
public class ConnectProcessService implements ProtocolProcessService {
    @Override
    public void process(Channel channel, MqttMessage msg) {
        // 消息解码器出现异常
        if (msg.decoderResult().isFailure()) {
            Throwable cause = msg.decoderResult().cause();
            if (cause instanceof MqttUnacceptableProtocolVersionException) {
                // 不支持的协议版本
                MqttConnAckMessage connAckMessage = (MqttConnAckMessage) MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                        new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_UNACCEPTABLE_PROTOCOL_VERSION, false), null);
                channel.writeAndFlush(connAckMessage);
                channel.close();
                return;
            } else if (cause instanceof MqttIdentifierRejectedException) {
                // 不合格的clientId
                MqttConnAckMessage connAckMessage = (MqttConnAckMessage) MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                        new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_IDENTIFIER_REJECTED, false), null);
                channel.writeAndFlush(connAckMessage);
                channel.close();
                return;
            }
            channel.close();
            return;
        }

        MqttConnectMessage connectMessage = (MqttConnectMessage) msg;


        // clientId为空或null的情况, 这里要求客户端必须提供clientId, 不管cleanSession是否为1, 此处没有参考标准协议实现
        if (StrUtil.isBlank(connectMessage.payload().clientIdentifier())) {
            MqttConnAckMessage connAckMessage = (MqttConnAckMessage) MqttMessageFactory.newMessage(
                    new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                    new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_IDENTIFIER_REJECTED, false), null);
            channel.writeAndFlush(connAckMessage);
            channel.close();
            return;
        }
        // 用户名和密码验证, 这里要求客户端连接时必须提供用户名和密码, 不管是否设置用户名标志和密码标志为1, 此处没有参考标准协议实现
        String username = connectMessage.payload().userName();
        String password = connectMessage.payload().passwordInBytes() == null ? null : new String(connectMessage.payload().passwordInBytes(), CharsetUtil.UTF_8);
        //校验密码
        if (!true) {
            MqttConnAckMessage connAckMessage = (MqttConnAckMessage) MqttMessageFactory.newMessage(
                    new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                    new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD, false), null);
            channel.writeAndFlush(connAckMessage);
            channel.close();
            return;
        }

//        // 如果会话中已存储这个新连接的clientId, 就关闭之前该clientId的连接
//        if (grozaSessionStoreService.containsKey(connectMessage.payload().clientIdentifier())){
//            SessionStore sessionStore = grozaSessionStoreService.get(msg.payload().clientIdentifier());
//            Channel previous = sessionStore.getChannel();
//            Boolean cleanSession = sessionStore.isCleanSession();
//            if (cleanSession){
//                grozaSessionStoreService.remove(msg.payload().clientIdentifier());
//                grozaSubscribeStoreService.removeForClient(msg.payload().clientIdentifier());
//                grozaDupPublishMessageStoreService.removeByClient(msg.payload().clientIdentifier());
//                grozaDupPubRelMessageStoreService.removeByClient(msg.payload().clientIdentifier());
//            }
//            previous.close();
//        }
//        //处理遗嘱信息
//        SessionStore sessionStore = new SessionStore(msg.payload().clientIdentifier(), channel, msg.variableHeader().isCleanSession(), null);
//        if (msg.variableHeader().isWillFlag()){
//            MqttPublishMessage willMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
//                    new MqttFixedHeader(MqttMessageType.PUBLISH,false, MqttQoS.valueOf(msg.variableHeader().willQos()),msg.variableHeader().isWillRetain(),0),
//                    new MqttPublishVariableHeader(msg.payload().willTopic(),0),
//                    Unpooled.buffer().writeBytes(msg.payload().willMessageInBytes())
//            );
//            sessionStore.setWillMessage(willMessage);
//        }
//        //处理连接心跳包 //netty 心跳探活
//        if (msg.variableHeader().keepAliveTimeSeconds() > 0){
//            if (channel.pipeline().names().contains("idle")){
//                channel.pipeline().remove("idle");
//            }
//            channel.pipeline().addFirst("idle",new IdleStateHandler(0, 0, Math.round(msg.variableHeader().keepAliveTimeSeconds() * 1.5f)));
//        }
//        //至此存储会话消息及返回接受客户端连接
//        grozaSessionStoreService.put(msg.payload().clientIdentifier(),sessionStore);
//        //将clientId存储到channel的map中
//        channel.attr(AttributeKey.valueOf("clientId")).set(msg.payload().clientIdentifier());
//        Boolean sessionPresent = grozaSessionStoreService.containsKey(msg.payload().clientIdentifier()) && !msg.variableHeader().isCleanSession();
//        MqttConnAckMessage okResp = (MqttConnAckMessage) MqttMessageFactory.newMessage(
//                new MqttFixedHeader(MqttMessageType.CONNACK,false,MqttQoS.AT_MOST_ONCE,false,0),
//                new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED,sessionPresent),
//                null
//        );
//        channel.writeAndFlush(okResp);
//        log.info("CONNECT - clientId: {}, cleanSession: {}", msg.payload().clientIdentifier(), msg.variableHeader().isCleanSession());
//        // 如果cleanSession为0, 需要重发同一clientId存储的未完成的QoS1和QoS2的DUP消息
//        if (!msg.variableHeader().isCleanSession()){
//            List<DupPublishMessageStore> dupPublishMessageStoreList = grozaDupPublishMessageStoreService.get(msg.payload().clientIdentifier());
//            List<DupPubRelMessageStore> dupPubRelMessageStoreList = grozaDupPubRelMessageStoreService.get(msg.payload().clientIdentifier());
//            dupPublishMessageStoreList.forEach(dupPublishMessageStore -> {
//                MqttPublishMessage publishMessage = (MqttPublishMessage)MqttMessageFactory.newMessage(
//                        new MqttFixedHeader(MqttMessageType.PUBLISH,true,MqttQoS.valueOf(dupPublishMessageStore.getMqttQoS()),false,0),
//                        new MqttPublishVariableHeader(dupPublishMessageStore.getTopic(),dupPublishMessageStore.getMessageId()),
//                        Unpooled.buffer().writeBytes(dupPublishMessageStore.getMessageBytes())
//                );
//                channel.writeAndFlush(publishMessage);
//            });
//            dupPubRelMessageStoreList.forEach(dupPubRelMessageStore -> {
//                MqttMessage pubRelMessage = MqttMessageFactory.newMessage(
//                        new MqttFixedHeader(MqttMessageType.PUBREL,true,MqttQoS.AT_MOST_ONCE,false,0),
//                        MqttMessageIdVariableHeader.from(dupPubRelMessageStore.getMessageId()),
//                        null
//                );
//                channel.writeAndFlush(pubRelMessage);
//            });
//        }

        MqttConnAckMessage okResp = (MqttConnAckMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED, true),
                null
        );
        channel.writeAndFlush(okResp);
        log.info("CONNECT - clientId: {}, cleanSession: {}", connectMessage.payload().clientIdentifier(), connectMessage.variableHeader().isCleanSession());
    }
}
