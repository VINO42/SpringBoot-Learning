package io.github.vino42.web;

import io.github.vino42.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/31 19:31
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    RedisService redisService;

    @RequestMapping("/set")
    public String testSet() {
        redisService.set("a", "b");
        redisService.expire("a", 1800, TimeUnit.SECONDS);
        Object o = redisService.get("a");
        System.out.println(o);
        return "ok";
    }
}
