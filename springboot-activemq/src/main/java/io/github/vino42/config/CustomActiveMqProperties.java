package io.github.vino42.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/21 21:05
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Configuration
public class CustomActiveMqProperties {
    @Value("${my.queue.text}")
    private String myQueueText;
    @Value("${my.queue.obj}")
    private String myQueueObj;

    public String getMyQueueText() {
        return myQueueText;
    }

    public String getMyQueueObj() {
        return myQueueObj;
    }
}
