package com.myredis.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/27 19:14
 * @Version 1.0
 **/
@Component
public class RedisUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 通过key获取对象
     *
     * @param key
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置redis键值对
     *
     * @param key
     * @param value
     */
    public Object set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("set error");
            e.printStackTrace();
            return false;
        }
    }

//    @Bean
//    @ConditionalOnClass(RedisUtil.class)
//    public RedisConnection redisConnection() {
//        return new DefaultStringRedisConnection()
//    }


    /**
     * 字节使用callback 和 redis 交互
     *
     * @return
     */
    public Object setUseCallback(String key, String value) {
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long size = connection.dbSize();
                logger.info("connection size :{}", size);
                // Can cast to StringRedisConnection if using a StringRedisTemplate
                return ((StringRedisConnection) connection).set(key, value);
            }
        });
    }
}
