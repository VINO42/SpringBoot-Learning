package io.github.vino42.handler;

import io.github.vino42.codec.MqttWebSocketCodec;
import io.github.vino42.contants.Constants;
import io.github.vino42.enums.SocketType;
import io.github.vino42.protocol.ProtocolProcessService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/28 22:02
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@AllArgsConstructor
public class MqttServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final Map<String, ProtocolProcessService> processServices;

    private final String socketType;

    static final Integer DEFAULT_MAX_BYTES_IN_MESSAGE = 65536;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();

        if (SocketType.MQTT.name().equals(socketType)) {
            pipeline.addLast("mqttDecoder", new MqttDecoder(DEFAULT_MAX_BYTES_IN_MESSAGE));
            pipeline.addLast("mqttEncoder", MqttEncoder.INSTANCE);
        }
        if (SocketType.MQTT_OVER_WS.name().equals(socketType)) {
            pipeline.addLast(new IdleStateHandler(30, 30, 30, TimeUnit.SECONDS));
            pipeline.addLast("httpServerCodec", new HttpServerCodec());
            /**
             * 我们通常接收到的是一个http片段，如果要想完整接受一次请求的所有数据，我们需要绑定HttpObjectAggregator，然后我们
             * 就可以收到一个FullHttpRequest-是一个完整的请求信息。
             * 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
             * 几乎在netty中的编程，都会使用到此hanler
             */
            pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(65536));
            //对写大数据流的支持
            pipeline.addLast(new ChunkedWriteHandler());

            WebSocketServerProtocolHandler webSocketHandler = new WebSocketServerProtocolHandler(Constants.WEB_SOCKET_PATH, Constants.MQTT_VERSION);
            pipeline.addLast("webSocketHandler", webSocketHandler);
            /**
             * websocket 压缩
             */
            pipeline.addLast(new WebSocketServerCompressionHandler());
            /**
             * mqtt over websocket 进行mqtt协议在websocket上的处理
             */
            pipeline.addLast(new WebsocketMqttHandler());
            /**
             * mqtt转帧处理
             */
            pipeline.addLast("mqttWebSocketCodec", new MqttWebSocketCodec());
            pipeline.addLast("mqttDecoder", new MqttDecoder(DEFAULT_MAX_BYTES_IN_MESSAGE));
            pipeline.addLast("mqttEncoder", MqttEncoder.INSTANCE);

        }
        /**
         *  真正的mqtt消息处理
         */
        pipeline.addLast(new MqttTransportHandler(processServices));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }
}
