package io.github.vino42.web;

import io.github.vino42.config.CustomActiveMqProperties;
import io.github.vino42.producer.MyProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/21 22:14
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RestController
@RequestMapping("/")
public class TextController {
    @Autowired
    MyProducer myProducer;
    @Autowired
    CustomActiveMqProperties activeMqProperties;

    @RequestMapping("/testText")
    public String testText() {
        myProducer.sendQueueTextMsg("ok", activeMqProperties.getMyQueueText());
        return "ok";
    }

    @RequestMapping("/testObj")
    public String testObj() {
        Map<String, Object> maps = new HashMap<>();
        maps.put("test", 123);
        maps.put("test1", "456");
        myProducer.sendQueueObjectMsg(maps, activeMqProperties.getMyQueueObj());
        return "ok";
    }
}
