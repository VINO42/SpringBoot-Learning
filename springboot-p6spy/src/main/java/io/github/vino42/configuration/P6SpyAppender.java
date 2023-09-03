package io.github.vino42.configuration;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.P6Logger;
import lombok.extern.slf4j.Slf4j;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/4 1:29
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 用于p6spy的自定义配置
 * =====================================================================================
 */
@Slf4j
public class P6SpyAppender implements P6Logger {
    protected P6SpyLoggerStrategy strategy;

    protected P6SpyAppender() {
        strategy = new P6SpyLoggerStrategy();
    }

    @Override
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql, String url) {
        logText("sql打印: "+strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql, url));
    }

    @Override
    public void logException(Exception e) {
        log.info("", e);
    }

    @Override
    public void logText(String text) {
        log.info(text);
        System.out.println(text);
    }

    @Override
    public boolean isCategoryEnabled(Category category) {
        return false;
    }
}
