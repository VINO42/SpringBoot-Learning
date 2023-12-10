package com.example.springbootsse.util;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
public class SseEmitterUtil {


    /**
     * 使用map对象，便于根据userId来获取对应的SseEmitter，或者放redis里面
     */
    private static Map<String, SseEmitter> USER_SESSION_MAP = new ConcurrentHashMap<>();


    /**
     * 创建用户连接并返回 SseEmitter
     *
     * @param userId   用户ID
     * @param response
     * @return SseEmitter
     */
    public static SseEmitter connect(String userId, HttpServletResponse response) {
        // 设置超时时间，0表示不过期。默认30秒，超过时间未完成会抛出异常：AsyncRequestTimeoutException
        SseEmitter sseEmitter = new SseEmitter(0L);
        // 注册回调
        sseEmitter.onCompletion(completionCallBack(userId, response));
        sseEmitter.onError(errorCallBack(userId));
        sseEmitter.onTimeout(timeoutCallBack(userId));
        log.info("创建新的sse连接，当前用户：{}", userId);
        USER_SESSION_MAP.put(userId,sseEmitter);
        return sseEmitter;
    }

    /**
     * 给指定用户发送信息
     */
    public static void sendMessage(String userId, String message) {
        if (USER_SESSION_MAP.containsKey(userId)) {
            try {
                // sseEmitterMap.get(userId).send(message, MediaType.APPLICATION_JSON);
                USER_SESSION_MAP.get(userId).send(message);
            } catch (IOException e) {
                log.error("用户[{}]推送异常:{}", userId, e.getMessage());
                removeUser(userId);
            }
        }
    }

    /**
     * 群发消息
     */
    public static void batchSendMessage(String wsInfo, List<String> ids) {
        ids.forEach(userId -> sendMessage(wsInfo, userId));
    }

    /**
     * 群发所有人
     */
    public static void batchSendMessage(String wsInfo) {
        USER_SESSION_MAP.forEach((k, v) -> {
            try {
                v.send(wsInfo, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                log.error("用户[{}]推送异常:{}", k, e.getMessage());
                removeUser(k);
            }
        });
    }

    /**
     * 移除用户连接
     */
    public static void removeUser(String userId) {
        USER_SESSION_MAP.remove(userId);
        log.info("移除用户：{}", userId);
    }

    /**
     * 获取当前连接信息
     */
    public static List<String> getIds() {
        return new ArrayList<>(USER_SESSION_MAP.keySet());
    }

    /**
     * 获取当前连接数量
     */
    public static int getUserCount() {
        return USER_SESSION_MAP.size();
    }

    private static Runnable completionCallBack(String userId, HttpServletResponse response) {
        return () -> {
            log.info("结束连接：{}", userId);
            ResponseEntity<String> resp = ResponseEntity.ok("建立连接");
            response.setContentType("text/event-stream");
            response.setCharacterEncoding("utf-8");
            try {
                PrintWriter pw = response.getWriter();
                pw.write("用户" + userId + "建立连接");
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            removeUser(userId);
        };
    }

    private static Runnable timeoutCallBack(String userId) {
        return () -> {
            log.info("连接超时：{}", userId);
            removeUser(userId);
        };
    }

    private static Consumer<Throwable> errorCallBack(String userId) {
        return throwable -> {
            log.info("连接异常：{}", userId);
            removeUser(userId);
        };
    }

}
