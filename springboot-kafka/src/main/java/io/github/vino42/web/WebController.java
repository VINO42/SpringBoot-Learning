package io.github.vino42.web;

import cn.hutool.core.util.RandomUtil;
import io.github.vino42.publiser.KafkaPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/30 21:32
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RestController
@RequestMapping("/")
public class WebController {
    @Autowired
    KafkaPublishService kafkaPublishService;

    @RequestMapping("/send")
    public String send() {
        IntStream.range(0, 10000).forEach(d -> {
            kafkaPublishService.send("test", RandomUtil.randomString(16));
        });
        return "ok";
    }

}
