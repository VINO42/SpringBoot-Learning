package io.github.vino42.service;

import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 封装的websocket相关的服务。一些关于websocket的操作
 */
@Component
public final class WebSocketService {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);
    // 存储 websocket session
    public static final Map<String, Session> ONLINE_USER_SESSIONS = new ConcurrentHashMap<>();

    /**
     * @param session 用户 session
     * @param message 发送内容
     */
    public static void sendMessage(Session session, String message) {
        if (session == null) {
            return;
        }
        final RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null) {
            return;
        }
        try {
            basic.sendText(message);
        } catch (IOException e) {
            logger.error("sendMessage IOException ", e);
        }
    }

    /**
     * 推送消息到其他客户端
     *
     * @param message
     */
    public void broadcast(String message) {
        ONLINE_USER_SESSIONS.forEach((sessionId, session) -> sendMessage(session, message));
    }

    public void disconectAllClient() {
        for (Map.Entry<String, Session> sessionEntry : ONLINE_USER_SESSIONS.entrySet()) {
            Session session = sessionEntry.getValue();
            try {
                session.close();
                logger.info("当前服务端将要下线,已主动断开 {} 的连接", sessionEntry.getKey());
            } catch (IOException e) {
                logger.error("服务端主动断开 {} 连接发生异常: {}", sessionEntry.getKey(), e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void disconnectSomeClientByServer(List<String> clientIds) {
        for (String userId : clientIds) {
            if (ONLINE_USER_SESSIONS.containsKey(userId)) {
                Session session = ONLINE_USER_SESSIONS.get(userId);
                logger.info("客户端需要重连，当前服务端已主动断开 {} 的连接", userId);
                try {
                    session.close();
                } catch (IOException e) {
                    logger.error("服务端主动断开 {} 连接发生异常: {}", userId, e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}