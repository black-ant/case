package com.gang.study.spring.cache.demo.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @Classname CacheConfig
 * @Description Encache
 * @Date 2020/6/18 9:37
 * @Created by zengzg
 */
//@EnableCaching
//@Configuration
public class CacheConfig {

    //    /**
    //     * 这里面的缓存名，是和后面@Cacheable(cacheNames = CACHE_NAME)绑定使用的
    //     */
    //    @Bean
    //    public CacheManager cacheManager() {
    //        return new ConcurrentMapCacheManager("person");
    //    }

}
