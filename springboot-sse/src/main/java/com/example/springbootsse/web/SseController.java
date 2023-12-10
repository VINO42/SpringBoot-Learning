package com.example.springbootsse.web;

import com.example.springbootsse.util.SseEmitterUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * =====================================================================================
 *
 * @Created :   2023/12/10 14:37
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RestController
@RequestMapping("/sse")
@Slf4j
public class SseController {


    /**
     * 用于创建连接
     */
    @GetMapping("/connect/{userId}")
    public SseEmitter connect(@PathVariable String userId, HttpServletResponse response) throws IOException {
        SseEmitter connect = SseEmitterUtil.connect(userId,response);
        return  connect;
    }

    /**
     * 推送给所有人
     *
     * @param message
     * @return
     */
    @GetMapping("/push/{message}")
    public ResponseEntity<String> push(@PathVariable(name = "message") String message) {
        // 获取连接人数
        int userCount = SseEmitterUtil.getUserCount();
        // 如果无在线人数，返回
        if (userCount < 1) {
            return ResponseEntity.status(500).body("无人在线！");
        }
        SseEmitterUtil.batchSendMessage(message);
        return ResponseEntity.ok("发送成功！");
    }
}
