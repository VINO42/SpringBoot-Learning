package io.github.vino42.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * =====================================================================================
 *
 * @Created :   2023/2/23 22:39
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/testlog", method = RequestMethod.GET)
    public void testLog() {
        logger.info("test接口");//最低拦截级别为warn，所以info不会输出发送到日志中心
        logger.error("error"); //会显示在日志中心，并邮件通知相关联系人
        int i=1/0;
    }


}
