package io.github.vino42.support;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by NewNet on 2017/02/16. guava 扩展的线程池 单例模式保证内存中只有一个线程池对象
 */
public class ThreadPoolGuavaExecutors {

    private Map<String, String> fixedContext;

    private ThreadPoolGuavaExecutors(Map<String, String> fixedContext) {
        this.fixedContext = fixedContext;
        getDefaultCompletedExecutorService();
    }

    private static ThreadPoolGuavaExecutors instance;

    private final int DEFAULT_MAX_THREAD = 500;
    public ListeningExecutorService defaultCompletedExecutorService = null;
    private static final Object lock = new Object();

    private ListeningExecutorService newCachedExecutorService(int maxThreadNumber, final String namePrefix) {
        return MoreExecutors.listeningDecorator(new ThreadPoolExecutor(0, maxThreadNumber, 30L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new ThreadFactory() {

            private final AtomicInteger poolNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(wrap(r, fixedContext), namePrefix + poolNumber.getAndIncrement());
                return thread;
            }
        }));

    }

    public void execute(Runnable runnable) {
        this.fixedContext=getContextForTask();
        defaultCompletedExecutorService.execute(runnable);
    }

    public ListenableFuture<?> submit(Runnable runnable) {
        this.fixedContext=getContextForTask();
        ListenableFuture<?> future = defaultCompletedExecutorService.submit(runnable);
        return future;
    }

    private ListeningExecutorService newCachedExecutorService(String namePrefix) {
        return newCachedExecutorService(DEFAULT_MAX_THREAD, namePrefix);
    }

    private void getDefaultCompletedExecutorService() {

        if (defaultCompletedExecutorService == null) {
            synchronized (lock) {
                if (defaultCompletedExecutorService == null) {
                    defaultCompletedExecutorService = newCachedExecutorService("therad-pool-io.github");
                }
            }
        }

    }

    public static ThreadPoolGuavaExecutors getInstance() {
        if (instance == null)
            instance = new ThreadPoolGuavaExecutors(getContextForTask());
        return instance;
    }

    private static Map<String, String> getContextForTask() {
        return MDC.getCopyOfContextMap();
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            Map previous = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                runnable.run();
            } finally {
                if (previous == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(previous);
                }
            }
        };
    }
}
