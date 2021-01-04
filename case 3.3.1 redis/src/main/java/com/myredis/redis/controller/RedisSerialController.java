package com.myredis.redis.controller;

import com.myredis.redis.entity.RedisSerialEnrity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Classname RedisSerialController
 * @Description TODO
 * @Date 2020/11/18 19:42
 * @Created by zengzg
 */
@RestController
@RequestMapping("serial")
public class RedisSerialController {


    private static final String redisKey = "123456";

    @Autowired
    @Qualifier(value = "serialRedisTemplate")
    private RedisTemplate template;

    @GetMapping("set")
    public String set() {
        RedisSerialEnrity serialEnrity = new RedisSerialEnrity();
        serialEnrity.setName("gang");
        template.boundValueOps(redisKey).set(serialEnrity, 500, TimeUnit.SECONDS);
        return "success";
    }

    @GetMapping("get")
    public RedisSerialEnrity get() {
        return (RedisSerialEnrity) template.boundValueOps(redisKey).get();
    }
}
