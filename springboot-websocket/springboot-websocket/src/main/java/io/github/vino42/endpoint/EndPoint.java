package io.github.vino42.endpoint;

import jakarta.websocket.Session;

import java.io.IOException;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/1 17:55
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : websocket 抽象接口
 * =====================================================================================
 */
public interface EndPoint {
    /**
     * 全局广播
     *
     * @param message
     */
    void broadcast(String message) throws IOException;

    /**
     * 发送消息给某人
     *
     * @param userId  用户id
     * @param message 消息体
     * @throws IOException
     */
    void sendMessageByUserId(String userId, String message) throws IOException;

    /**
     * 关闭链接
     *
     * @param clientId 客户端唯一标识
     * @param session  websocket的session
     */
    void onClose(String clientId, Session session);

    /**
     * 开启链接
     *
     * @param session  websocket 的session
     * @param clientId 客户端唯一标识
     */
    void onOpen(Session session, String clientId) throws IOException;

    /**
     * 接收客户端消息
     *
     * @param clientId
     * @param message
     * @param session
     */
    void onMessage(String clientId, String message, Session session) throws IOException;

    /**
     * 异常监听
     *
     * @param session
     * @param error
     */
    void onError(Session session, Throwable error);

    /**
     * 在线设备数
     *
     * @return
     */
    int getOnlineCount();
}
