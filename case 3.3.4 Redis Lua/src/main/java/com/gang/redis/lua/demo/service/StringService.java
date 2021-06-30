package com.gang.redis.lua.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Classname ArrayService
 * @Description TODO
 * @Date 2021/6/29
 * @Created by zengzg
 */
@Component
public class StringService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisScript<String> script;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        doStringSimple();
    }

    public void doStringSimple() {

        this.redisTemplate.opsForValue().set("firstStr", "Ant");
        this.redisTemplate.opsForValue().set("secondStr", "Black");

        logger.info("------> doStringSimple 执行 String 返回基本案例 <-------");
        String response = this.redisTemplate.execute(script, Arrays.asList("firstStr"), "Good");
        logger.info("------> doStringSimple 获取 String Reponse :{} <-------", response);
    }
}
