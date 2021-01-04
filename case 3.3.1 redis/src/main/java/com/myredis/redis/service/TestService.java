package com.myredis.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/26 16:33
 * @Version 1.0
 **/
@Service
public class TestService {

    //StringRedisTemplate使用的是StringRedisSerializer

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //RedisTemplate使用的是JdkSerializationRedisSerializer
    //此时存入的为字节数组 ，取出后会将字节码转换为 相关对象
    // 当！存入的数据不是字节数组,而是可见字符串的时候 ，就会导致取出的为null
    //    @Autowired
    //    RedisTemplate<String,String> redisTemplate;

    // RedisTemplate 如果没有 预定义泛型 ，查出来的为null , 可以屏蔽后尝试
    @Autowired
    RedisTemplate redisTemplate;

    public String findByKey(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public String saveByKey(String key, String data) {
        stringRedisTemplate.opsForValue().set(key, data);
        return "ok";
    }

    public Object findByKeyTemp1(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object saveByKeyTemp1(String key, String data) {
        redisTemplate.opsForValue().set(key, data);
        return "ok";
    }

    public Object findByKeyTemp2(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object saveByKeyTemp2(String key, String data) {
        redisTemplate.opsForValue().set(key, data);
        return "ok";
    }

}
