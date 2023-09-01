package io.github.vino42.endpoint;

import cn.hutool.core.util.StrUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.WebSocket;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.vino42.util.WebSocketUtils.ONLINE_USER_SESSIONS;

/**
 * 用户聊天室 会议endpoint
 */

@ServerEndpoint(value = "/ws/{userId}")
@Component
public class UserMeetingWebSocketEndPoint implements EndPoint {
    private final static Logger logger = LogManager.getLogger(WebSocket.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
     */

    private static AtomicInteger onlineCount = new AtomicInteger(0);


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    @Override
    public void onOpen(Session session, @PathParam(value = "userId") String userId) throws IOException {
        //加入map
        ONLINE_USER_SESSIONS.put(userId, session);
        onlineCount.set(ONLINE_USER_SESSIONS.entrySet().size());
        logger.info("用户{}连接成功,当前在线人数为{}", userId, getOnlineCount());
        broadcast("用户[" + userId + " 上线了] ");
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    @Override
    public void onClose(@PathParam("userId") String userId, Session session) {
        //从map中删除
        ONLINE_USER_SESSIONS.remove(userId);
        onlineCount.getAndDecrement();

        logger.info("用户{}关闭连接！当前在线人数为{}", userId, getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    @Override
    public void onMessage(@PathParam(value = "userId") String userId, String message, Session session) throws IOException {
        logger.info("来自客户端用户：{} 消息:{}", userId, message);
        broadcast("用户[" + userId + "] : " + message);

    }

    /**
     * 发生错误时调用
     *
     * @OnError
     */
    @OnError
    @Override
    public void onError(Session session, Throwable error) {
        try {
            session.close();
        } catch (IOException e) {
            logger.error("onError excepiton", e);
        }
        logger.info("Throwable msg " + error.getMessage());
    }


    /**
     * 通过userId向客户端发送消息
     */
    @Override
    public void sendMessageByUserId(String userId, String message) throws IOException {
        logger.info("服务端发送消息到{},消息：{}", userId, message);
        if (StrUtil.isNotBlank(userId) && ONLINE_USER_SESSIONS.containsKey(userId)) {
            ONLINE_USER_SESSIONS.get(userId).getBasicRemote().sendText(message);
        } else {
            logger.error("用户{}不在线", userId);
        }

    }

    /**
     * 通过userId向客户端发送消息
     */
    @Override
    public void broadcast(String message) throws IOException {
        logger.info("服务端发送消息到所有用户,消息：{}", message);

        ONLINE_USER_SESSIONS.entrySet().stream().forEach(d -> {
            try {
                d.getValue().getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("send message error , message:{},userId:{}", message, d);
            }
        });

    }

    @Override
    public synchronized int getOnlineCount() {
        return onlineCount.get();
    }

}


