package io.github.vino42.config;

import io.github.vino42.support.ThreadPoolGuavaExecutors;
import org.slf4j.MDC;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/13 20:26
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Configuration
public class AsyncPoolConfig implements AsyncConfigurer {
    public static final String TASK_EXECUTOR_NAME = "ASYNC_TASK_EXECUTOR";


    @Override
    @Bean(TASK_EXECUTOR_NAME)
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数（默认线程数）
        executor.setCorePoolSize(10);
        // 最大线程数
        executor.setMaxPoolSize(120);
        // 缓冲队列数
        executor.setQueueCapacity(200);
        // 允许线程空闲时间（单位：默认为秒）
        executor.setKeepAliveSeconds(60);
        // 线程池名前缀
        executor.setThreadNamePrefix("asyncExecutor-");
        // 设置是否等待计划任务在关闭时完成 等待任务在关机时完成--表明等待所有线程执行完
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置此执行器应该阻止的最大秒数
        executor.setAwaitTerminationSeconds(60);
        // 增加 TaskDecorator 属性的配置
        // 此处会报错 No thread-bound request found: Are you referring to request attributes outside of an actual web request, or processing a request outside of the originally receiving thread? If you are actually operating within a web request and still receive this message, your code is probably running outside of DispatcherServlet: In this case, use RequestContextListener or RequestContextFilter to expose the current request.
        executor.setTaskDecorator(new ContextCopyingDecorator());
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
    }

    class ContextCopyingDecorator implements TaskDecorator {


        @Override
        public Runnable decorate(Runnable runnable) {
            try {
                RequestAttributes context = RequestContextHolder.currentRequestAttributes();  //1
                Map<String, String> previous = MDC.getCopyOfContextMap();                      //2
//                SecurityContext securityContext = SecurityContextHolder.getContext();          //3
                return () -> {
                    try {
                        RequestContextHolder.setRequestAttributes(context, true);     //1
                        MDC.setContextMap(previous);                         //2
//                        SecurityContextHolder.setContext(securityContext);   //3
                        runnable.run();
                    } finally {
                        RequestContextHolder.resetRequestAttributes();        // 1
                        MDC.clear();                                        // 2
//                        SecurityContextHolder.clearContext();                // 3
                    }
                };
            } catch (IllegalStateException e) {
                return runnable;
            }
        }
    }

    @Bean
    public ThreadPoolGuavaExecutors threadPoolGuavaExecutors() {
        ThreadPoolGuavaExecutors instance = ThreadPoolGuavaExecutors.getInstance();
        return instance;
    }
}
