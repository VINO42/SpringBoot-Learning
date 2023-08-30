package io.github.vino42.listener;

import io.github.vino42.event.SyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


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
public class SyncEventListener {


    private static final Logger LOGGER = LoggerFactory.getLogger(SyncEventListener.class);

    @EventListener()
    public void syncListen(SyncEvent syncEvent) {
        String msg = syncEvent.getMsg();
        LOGGER.info("[syncListen],msg:{}", msg);
    }

}
