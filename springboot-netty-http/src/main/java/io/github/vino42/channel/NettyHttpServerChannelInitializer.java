package io.github.vino42.channel;

import io.github.vino42.handler.NettyHttpServerHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/31 20:58
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription : netty channle定义
 * =====================================================================================
 */
@ChannelHandler.Sharable
@Slf4j
public class NettyHttpServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();

        /**
         * 或使用HttpRequestDecoder & HttpResponseEncoder
         */
        pipeline.addLast(new HttpServerCodec());
        /**
         * 在处理POST消息体时需要加上
         * 多个消息体的aggregate
         */
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast(new HttpServerExpectContinueHandler());
        /**
         * 自定义nettyHttpHandler
         */
        pipeline.addLast(new NettyHttpServerHandler());
    }
}
