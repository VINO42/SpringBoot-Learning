package io.github.vino42.web;

import io.github.vino42.response.ServiceResult;
import io.github.vino42.support.TestDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * =====================================================================================
 *
 * @Created :   2023/8/31 22:54
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RestController
@RequestMapping("/test")
public class WebController /*implements ApplicationContextAware*/ {

    @RequestMapping("/testStr")
    public ServiceResult testStr() {
        return ServiceResult.ok("ok");
    }

    @RequestMapping("/testQuery")
    public ServiceResult testStr(@RequestParam String query) {
        return ServiceResult.ok("okQuery");
    }

    @RequestMapping("/testDto")
    public ServiceResult testPoJo() {
        TestDto dto = new TestDto();
        dto.setAge(10);
        dto.setName("name");

        return ServiceResult.ok(dto);
    }

    @RequestMapping("/testPost")
    public ServiceResult testPoJo(@RequestBody TestDto testDto) {

        return ServiceResult.ok(testDto);
    }
}
