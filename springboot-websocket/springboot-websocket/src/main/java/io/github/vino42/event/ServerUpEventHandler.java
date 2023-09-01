package io.github.vino42.event;

import cn.hutool.core.net.NetUtil;
import cn.hutool.json.JSONUtil;
import io.github.vino42.service.KafkaPublishService;
import io.github.vino42.service.WebSocketService;
import io.github.vino42.util.KafkaServiceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/1 18:48
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription : 服务启动监听器 ContextStartedEvent 不起作用 改为 ApplicationRunner
 * =====================================================================================
 */
@Component
@Slf4j
public class ServerUpEventHandler implements ApplicationRunner {
    @Autowired
    KafkaPublishService kafkaPublishService;
    @Autowired
    WebSocketService webSocketService;


    @Override
    public void run(ApplicationArguments args) {
        log.info("WebSocket实例发布服务上线消息：通知网关更新哈希环");

        KafkaServiceMessage message = new KafkaServiceMessage();
        message.setServiceIp(NetUtil.getLocalhostStr());
        message.setServiceStatus(1);
        message.setTimestamp(System.currentTimeMillis());
        kafkaPublishService.send("service_up_down", JSONUtil.toJsonStr(message));
    }
}
