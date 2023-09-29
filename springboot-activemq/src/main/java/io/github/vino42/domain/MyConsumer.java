package io.github.vino42.domain;

import io.github.vino42.config.CustomActiveMqProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/21 22:10
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Component
public class MyConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyConsumer.class);

    @Autowired
    CustomActiveMqProperties activeMqProperties;

    @JmsListener(destination = "${my.queue.text}", containerFactory = "jmsListenerContainerQueue")    //用这个注解去监听 监听的队列
    public void receiveText(String msg) {
        System.out.println("消费者成功获取到生产者的消息，msg" + msg);
    }

    @JmsListener(destination = "${my.queue.obj}", containerFactory = "jmsListenerContainerQueue")    //用这个注解去监听 监听的队列
    public void receiveObj(Object msg) {
        System.out.println("消费者成功获取到生产者的消息，msg" + msg);
    }
}
