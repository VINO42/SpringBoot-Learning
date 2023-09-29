package io.github.vino42.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/29 1:31
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription : mqtt over websocket codec
 * =====================================================================================
 */
@ChannelHandler.Sharable
public class MqttWebSocketCodec extends MessageToMessageEncoder<ByteBuf> {


    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if (msg == null) {
            return;
        }
        ctx.channel().writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(msg)));
    }
}
