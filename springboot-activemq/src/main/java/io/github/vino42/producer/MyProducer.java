package io.github.vino42.producer;

import io.github.vino42.config.CustomActiveMqProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import static java.lang.Boolean.TRUE;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/21 22:02
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Component
public class MyProducer {
    @Autowired
    @Lazy
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    CustomActiveMqProperties activeMqProperties;

    public boolean sendQueueTextMsg(String textMsg, String dest) {
        jmsMessagingTemplate.convertAndSend(dest, textMsg);
        return TRUE;
    }

    public boolean sendQueueObjectMsg(Object objectMsg, String dest) {
        jmsMessagingTemplate.convertAndSend(dest, objectMsg);
        return TRUE;
    }
}
