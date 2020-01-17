package com.myredis.redis.service;

import com.myredis.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/27 19:47
 * @Version 1.0
 **/
@Service
public class UseUtilService {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisUtil redisUtil;

    public Object saveByKey(String key, String data) {
        return redisUtil.set(key, data);
    }

    public Object getByKey(String key) {
        return redisUtil.get(key);
    }

    /**
     * set Use callback
     */
    public Object useCallback(String key, String data){
        return redisUtil.setUseCallback(key,data);
    }
}
