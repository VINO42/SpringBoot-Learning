package io.github.vino42.listener;

import io.github.vino42.event.ASyncTransEvent;
import io.github.vino42.event.AsyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


/**
 * =====================================================================================
 *
 * @Created :   2022/3/13 19:35
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : 
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Component
public class AsyncEventListener {


    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncEventListener.class);

    @Async
    @EventListener()
    public void asyncListen(AsyncEvent syncEvent) {
        String msg = syncEvent.getMsg();
        LOGGER.info("[asyncListen],msg:{}", msg);
    }

    /**
     * @param syncEvent
     * @see TransactionPhase
     * fallbackExecution = true 没有开启事务的pub 也会消费到
     */
    @Async
    @TransactionalEventListener
    public void asyncTranListen(ASyncTransEvent syncEvent) {
        String msg = syncEvent.getMsg();

        LOGGER.info("[transAsyncListen],msg:{}", msg);
    }
}
