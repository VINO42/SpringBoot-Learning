package io.github.vino42.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/30 23:21
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : mvn clean -DskipTests  spring-boot:build-image -Pnative
 * =====================================================================================
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String test() {
        return "hello native";
    }
}
