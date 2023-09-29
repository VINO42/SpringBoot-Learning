package io.github.vino42.handler;

import io.github.vino42.protocol.ProtocolProcessService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Map;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/28 22:04
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription : mqtt 消息处理器
 * =====================================================================================
 */
@AllArgsConstructor
@ChannelHandler.Sharable
public class MqttTransportHandler extends SimpleChannelInboundHandler<MqttMessage> {
    private final Map<String, ProtocolProcessService> processServices;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) {

        //TODO: opt
        if (msg.decoderResult().isFailure()) {
            Throwable cause = msg.decoderResult().cause();
            if (cause instanceof MqttUnacceptableProtocolVersionException) {
                ctx.writeAndFlush(MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                        new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_UNACCEPTABLE_PROTOCOL_VERSION, false),
                        null));
            } else if (cause instanceof MqttIdentifierRejectedException) {
                ctx.writeAndFlush(MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                        new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_IDENTIFIER_REJECTED, false),
                        null));
            }
            ctx.close();
            return;
        }
        MqttMessageType inputMsgType = msg.fixedHeader().messageType();

        ProtocolProcessService protocolProcessService = processServices.get(inputMsgType.name().toUpperCase());
        protocolProcessService.process(ctx.channel(), msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            // 远程主机强迫关闭了一个现有的连接的异常
            ctx.close();
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.ALL_IDLE) {
                Channel channel = ctx.channel();
                String clientId = (String) channel.attr(AttributeKey.valueOf("clientId")).get();
                // 发送遗嘱消息
//                if (this.protocolProcess.getGrozaSessionStoreService().containsKey(clientId)) {
//                    SessionStore sessionStore = this.protocolProcess.getGrozaSessionStoreService().get(clientId);
//                    if (sessionStore.getWillMessage() != null) {
//                        this.protocolProcess.publish().processPublish(ctx.channel(), sessionStore.getWillMessage());
//                    }
//                }
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
