package io.github.vino42.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/11 21:11
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Configuration(proxyBeanMethods = false)
public class CaffeineConfig {
    @Bean("caffeineCacheManager")
    public CacheManager cacheManager(CacheLoader cacheLoader) {

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(60, TimeUnit.SECONDS)
                //写入后多久刷新
                .refreshAfterWrite(Duration.ofMinutes(1))
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
        );
        //是否允许空值
        cacheManager.setAllowNullValues(true);
        cacheManager.setCacheLoader(cacheLoader);
        return cacheManager;

    }

    /**
     * 如果配置refreshAfterWrite要有这个bean，
     * 否则报错java.lang.IllegalStateException: refreshAfterWrite requires a LoadingCache
     *
     * @return
     */
    @Bean
    public CacheLoader<Object, Object> cacheLoader() {
        CacheLoader<Object, Object> cacheLoader = new CacheLoader<Object, Object>() {
            @Nullable
            @Override
            public Object load(@NonNull Object key) throws Exception {
                return null;
            }
        };
        return cacheLoader;
    }
}
