package io.github.vino42.web;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import io.github.vino42.config.User;
import io.github.vino42.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    @RequestMapping("/testLua1")
    public String testLua1() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String script = "local key = KEYS[1]\n" +
                "local values =ARGV\n" +
                "for i, value in ipairs(values) do\n" +
                "    redis.call('LPUSH', key, value)\n" +
                "end\n" +
                "return redis.call('LLEN', key)";
        List<String> ar = Lists.newArrayList();
        for (int i = 100; i >0 ; i--) {
            ar.add("a" + i);
        }
        Long l = redisService.luaPipe(script, "test456", ar);
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();

        return "ok--"+l+"-----"+totalTimeMillis;
    }

    @RequestMapping("/testLua2")
    public String testLua2() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> ar = Lists.newArrayList();
        for (int i = 100; i >0 ; i--) {
            ar.add("a" + i);
        }
        String script = "";
        Long l = redisService.leftPushAll("test123", ar);
        System.out.println(l);
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        return "ok2---" + l+"----"+totalTimeMillis;

    }

    @RequestMapping("/setJson")
    public String setJson() {
        List<User> list= Lists.newArrayList();
        for (int i = 0; i < 2000; i++) {
            User user=new User();
            user.setName(RandomUtil.randomString(8));
            user.setNickName(RandomUtil.randomString(12));
            list.add(user);
        }
        redisService.set("test1", list);
//        List<User> o = (List<User>) redisService.get("test");
        return "ok";
    }
}
