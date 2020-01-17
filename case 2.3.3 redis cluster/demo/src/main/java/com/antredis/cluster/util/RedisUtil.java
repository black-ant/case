package com.antredis.cluster.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/26 11:49
 * @Version 1.0
 **/
@Component
public class RedisUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 通过key获取对象
     *
     * @param key
     */
    public Object get(String key) {
        logger.info("KEY IS :{}",key);
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
            e.printStackTrace();
            return false;
        }
    }
}
