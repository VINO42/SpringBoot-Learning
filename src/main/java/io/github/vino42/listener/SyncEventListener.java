package io.github.vino42.listener;

import io.github.vino42.event.ASyncTransEvent;
import io.github.vino42.event.SyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;


/**
 * =====================================================================================
 *
 * @Created :   2022/3/13 19:35
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Component
public class SyncEventListener {


    private static final Logger LOGGER = LoggerFactory.getLogger(SyncEventListener.class);

    @Async
    @EventListener()
    public void syncListen(SyncEvent syncEvent) {
        String msg = syncEvent.getMsg();
        LOGGER.info("[syncListen],msg:{}", msg);
    }

    /**
     * @param syncEvent
     * @se TransactionPhase
     */
    @Async
    @TransactionalEventListener
    public void tranListen(ASyncTransEvent syncEvent) {
        String msg = syncEvent.getMsg();

        LOGGER.info("[transAsyncListen],msg:{}", msg);
    }
}
