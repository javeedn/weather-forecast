package com.test.weather.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The CacheConfiguration class in Java creates a cache manager using ConcurrentMapCacheManager for a
 * cache named "weatherCache".
 * 
 * @author N Javeed
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    @Value("${spring.cache.evict.time.minutes}")
    private int minutes;

    /**
     * The function creates a cache manager using ConcurrentMapCacheManager for a cache named
     * "weatherCache" in Java.
     * 
     * @return A CacheManager bean is being returned, specifically a ConcurrentMapCacheManager bean
     * with the name "weatherCache".
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("weatherCache");
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(minutes, TimeUnit.MINUTES));
        return cacheManager;
    }

}