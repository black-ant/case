package com.gang.redis.lua.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/6/29
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MoneyTransferService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        booleanSimple();
    }

    public void booleanSimple() {
        logger.info("------> 执行 Lua 基本逻辑 StartService <-------");

        // initialize few accounts
        this.redisTemplate.opsForHash().put("account", "a", "100");
        this.redisTemplate.opsForHash().put("account", "b", "20");

        // transfer money with lua script
        this.service.transfer("a", "b", 20);

        // check the results
        logger.info("------> [从 Redis 中获取参数 a:{}] <-------", this.redisTemplate.opsForHash().get("account", "a"));
        logger.info("------> [从 Redis 中获取参数 b:{}] <-------", this.redisTemplate.opsForHash().get("account", "b"));
    }
}
