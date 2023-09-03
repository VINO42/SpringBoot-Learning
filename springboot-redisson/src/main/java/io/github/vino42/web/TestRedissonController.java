package io.github.vino42.web;

import io.github.vino42.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/3 22:39
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RequestMapping("/")
@RestController
public class TestRedissonController {

    @Autowired
    RedisService redisService;
    /**
     * RedissonAutoConfiguration 会自动组装StringRedistemplate、RedisTemplate
     * 但是还是建议自定义RedisTemplate
     */
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @RequestMapping("test1")
    public String testRedissoon() {
        redisService.rmapSet("a", "b", "123");
        Object o = redisService.rmapGet("a", "b");
        System.out.println(o);
        stringRedisTemplate.opsForValue().set("c","d",10000, TimeUnit.SECONDS);
        String s = stringRedisTemplate.opsForValue().get("c");
        System.out.println(s);
        return "ok";
    }
}
