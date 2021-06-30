package com.gang.redis.lua.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @Classname ArrayService
 * @Description TODO
 * @Date 2021/6/29
 * @Created by zengzg
 */
@Component
public class ArrayService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisScript<String[]> script;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        doArraySimple();
        doArrayBack();
    }

    public void doArraySimple() {
        logger.info("------> ArrayService 执行 Array 返回基本案例 <-------");
        String[] response = this.redisTemplate.execute(script, Arrays.asList("test"), "value");
        logger.info("------> ArrayService 获取 Array Reponse :{} <-------", response);
    }


    public void doArrayBack() {
        Resource scriptSource = new ClassPathResource("scripts/backArrayInfo.lua");
        RedisScript<String[]> redisScript = RedisScript.of(scriptSource, String[].class);
        String[] backArrat = this.redisTemplate.execute(redisScript, Arrays.asList("10"), "100");
        logger.info("------> doArrayBack 执行 :{} <-------", backArrat);
    }
}
