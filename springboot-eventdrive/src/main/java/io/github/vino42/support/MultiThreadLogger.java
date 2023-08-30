package io.github.vino42.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiThreadLogger {
    private static final ThreadLocal<Logger> LOGGER = new ThreadLocal<>();

    public static void debug(String message) {
        getLogger().debug(message);
    }

    public static void info(String message) {
        getLogger().info(message);
    }

    public static void warn(String message) {
        getLogger().warn(message);
    }

    public static void error(String message) {
        getLogger().error(message);
    }

    public static Logger getLogger() {
        Logger logger = LOGGER.get();
        if (logger == null) {
            logger = LoggerFactory.getLogger(Thread.currentThread().getName());
            LOGGER.set(logger);
        }
        return logger;
    }
}