package com.gang.study.redis.test.demo.config;

import io.lettuce.core.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname BaseRedisConfig
 * @Description TODO
 * @Date 2019/12/18 17:10
 * @Created by zengzg
 */

public class CachingConfigurerSupport implements CachingConfigurer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public CacheManager cacheManager() {
        logger.info("------> this is in cacheManager <-------");
        return null;
    }

    @Override
    public KeyGenerator keyGenerator() {
        logger.info("------> this is in keyGenerator <-------");
        return null;
    }

    @Override
    public CacheResolver cacheResolver() {
        logger.info("------> this is in cacheResolver <-------");
        return null;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        logger.info("------> this is in errorHandle <-------");
        return null;
    }

}

