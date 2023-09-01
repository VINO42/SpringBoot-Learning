package io.github.vino42.web;

import io.github.vino42.endpoint.UserMeetingWebSocketEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 测试用的web控制器
 */
@RestController
@RequestMapping("/")
public class WebSocketController {
    @Autowired
    private UserMeetingWebSocketEndPoint webSocketEndpoint;

    @PostMapping("/sentMessage")
    public void sentMessage(String userId, String message) {
        try {
            webSocketEndpoint.sendMessageByUserId(userId, message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/broadcast")
    public void broadcast(String message) {
        try {
            webSocketEndpoint.broadcast(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

