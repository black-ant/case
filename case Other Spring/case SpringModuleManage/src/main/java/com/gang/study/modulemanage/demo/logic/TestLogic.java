package com.gang.study.modulemanage.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/2/7 16:50
 * @Created by zengzg
 */

@RestController
@RequestMapping("/test")
public class TestLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/get")
    private String get() {
        logger.info("------> this is in get :{}<-------", redisTemplate);

        logger.info("------> this is in get :{} <-------", redisTemplate.opsForValue().get("111"));
        return "ok";
    }
}
