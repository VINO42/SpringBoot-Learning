package io.github.vino42.support;

import io.github.vino42.web.TestController;
import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandle(Exception e) { // 处理方法参数的异常类型
        Sentry.captureException(e);
        logger.error("3333");
        return null;
    }

}