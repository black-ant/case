package com.gang.study.modulemanage.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Classname RedisConfig
 * @Description TODO
 * @Date 2020/2/7 17:24
 * @Created by zengzg
 */
@Configuration
public class RedisConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        logger.info("------> this is in StringRedisTemplate <-------");
        try {
            redisConnectionFactory.getConnection();
        } catch (Exception e) {
            logger.info("------> this is in Connection <-------");
        }
        logger.info("------> this is in Connection ok<-------");
        return new StringRedisTemplate(redisConnectionFactory);
    }


}
