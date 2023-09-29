package io.github.vino42.configuration;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;
import com.p6spy.engine.spy.appender.P6Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class P6SpyAppender implements P6Logger {
    private Logger log;


    private P6SpyLoggerStrategy strategy;

    public P6SpyAppender() {
        strategy = new P6SpyLoggerStrategy();
        log = LoggerFactory.getLogger("sql-print");
    }

    @Override
    public void logSQL(int connectionId, String now, long elapsed,
                       Category category, String prepared, String sql, String url) {
        final String msg = strategy.formatMessage(connectionId, now, elapsed,
                category.toString(), prepared, sql, url);

        if (Category.ERROR.equals(category)) {
            log.error(msg);
        } else if (Category.WARN.equals(category)) {
            log.warn(msg);
        } else if (Category.DEBUG.equals(category)) {
            log.debug(msg);
        } else {
            log.info(msg);
        }
    }

//    @Override
//    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql, String url) {
//        logText("sql打印: "+strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql, url));
//    }

    @Override
    public void logException(Exception e) {
        log.info("", e);
    }

    @Override
    public void logText(String text) {
        log.info(text);
    }

    @Override
    public boolean isCategoryEnabled(Category category) {
        if (Category.ERROR.equals(category)) {
            return log.isErrorEnabled();
        } else if (Category.WARN.equals(category)) {
            return log.isWarnEnabled();
        } else if (Category.DEBUG.equals(category)) {
            return log.isDebugEnabled();
        } else {
            return log.isInfoEnabled();
        }
    }
}
