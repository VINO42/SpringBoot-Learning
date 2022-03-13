package io.github.vino42.web;

import cn.hutool.core.thread.ThreadUtil;
import io.github.vino42.event.SyncEvent;
import io.github.vino42.support.ThreadPoolGuavaExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/13 19:42
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RestController
@RequestMapping("/")
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ThreadPoolGuavaExecutors threadPoolGuavaExecutors;

    @RequestMapping("/syncPub")
    public String syncPub() {
        LOGGER.info("[syncPub],msg:{}", "hello");
        SyncEvent event = new SyncEvent("hello");
        applicationEventPublisher.publishEvent(event);
        return "ok syncPub";
    }

    @RequestMapping("/asyncPub")
    public String asyncPub() {
        LOGGER.info("[asyncPub],msg:{}", "hello");
        SyncEvent event = new SyncEvent("hello");
        applicationEventPublisher.publishEvent(event);
        return "ok asyncPub";
    }

    @RequestMapping("/asyncTranPub")
    @Transactional
    public String asyncTranPub() {
        LOGGER.info("[asyncTranPub],msg:{}", "hello");
        SyncEvent event = new SyncEvent("hello");
        applicationEventPublisher.publishEvent(event);
        ThreadUtil.execAsync(new RunJob());
        threadPoolGuavaExecutors.execute(new RunJob());
        return "ok asyncTranPub";
    }

    class RunJob implements Runnable {
        @Override
        public void run() {
            LOGGER.info("[runable................]");
        }
    }
}

