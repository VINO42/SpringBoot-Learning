package io.github.vino42;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * =====================================================================================
 *
 * @Created :   2023/6/23 0:49
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RestController
@Slf4j(topic = "test")
public class TestController {

    @RequestMapping("/hello")
    public String test() {
        log.info("request for native ");
        return "hello native";
    }
}
