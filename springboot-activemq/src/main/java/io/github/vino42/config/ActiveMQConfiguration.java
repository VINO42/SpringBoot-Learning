package io.github.vino42.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;


/**
 * =====================================================================================
 *
 * @Created :   2022/3/21 22:04
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Configuration
public class ActiveMQConfiguration {
    @Autowired
    CustomActiveMqProperties activeMqProperties;

    @Bean
    public ActiveMQQueue myQueueText() {
        return new ActiveMQQueue(activeMqProperties.getMyQueueText());
    }

    @Bean
    public ActiveMQQueue myQueueObj() {
        return new ActiveMQQueue(activeMqProperties.getMyQueueObj());
    }

    /**
     * 发布-订阅模式的ListenerContainer
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory factory,
                                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        configurer.configure(jmsListenerContainerFactory, factory);
        jmsListenerContainerFactory.setConnectionFactory(factory);
        return jmsListenerContainerFactory;
    }

    /**
     * P2P模式的ListenerContainer
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ConnectionFactory factory,
                                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        configurer.configure(jmsListenerContainerFactory, factory);
        jmsListenerContainerFactory.setConnectionFactory(factory);
        return jmsListenerContainerFactory;
    }
}
