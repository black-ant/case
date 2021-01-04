package com.myredis.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/27 19:12
 * @Version 1.0
 **/
public class RedisStringUntil {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

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
    public Object set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
