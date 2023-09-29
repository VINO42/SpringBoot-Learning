package io.github.vino42.server;

import io.github.vino42.handler.MqttServerChannelInitializer;
import io.github.vino42.protocol.ProtocolProcessService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/28 20:26
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Component
@Slf4j
public class MqttServer implements ApplicationRunner {
    @Autowired
    private Environment environment;
    @Autowired
    private Map<String, ProtocolProcessService> processServices;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        int nettyPort = Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port", "8080"))) + 1;
        String socketType = Objects.requireNonNull(environment.getProperty("server.socketType", "MQTT"));

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new MqttServerChannelInitializer(processServices, socketType));

            Channel ch = bootstrap.bind(nettyPort).sync().channel();
            log.info("Netty MQTT server listening on port " + nettyPort);
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
