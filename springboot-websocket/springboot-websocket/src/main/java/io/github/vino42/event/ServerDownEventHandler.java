package io.github.vino42.event;

import cn.hutool.core.net.NetUtil;
import cn.hutool.json.JSONUtil;
import io.github.vino42.service.KafkaPublishService;
import io.github.vino42.service.WebSocketService;
import io.github.vino42.util.KafkaServiceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/1 18:47
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription : 当前websocket服务下线的监听
 * =====================================================================================
 */
@Component
@Slf4j
public class ServerDownEventHandler implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    KafkaPublishService kafkaPublishService;
    @Autowired
    WebSocketService webSocketService;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("WebSocket实例发布服务下线消息：通知网关更新哈希环");
        KafkaServiceMessage message = new KafkaServiceMessage();
        message.setServiceIp(NetUtil.getLocalhost().toString());
        message.setServiceStatus(0);
        message.setTimestamp(System.currentTimeMillis());
        kafkaPublishService.send("service_up_down", JSONUtil.toJsonStr(message));
        webSocketService.disconectAllClient();
    }
}
