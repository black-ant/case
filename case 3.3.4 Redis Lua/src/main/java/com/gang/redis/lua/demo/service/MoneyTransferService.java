package com.gang.redis.lua.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @Classname MoneyTransferService
 * @Description TODO
 * @Date 2021/6/29
 * @Created by zengzg
 */
@Service
public class MoneyTransferService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisScript<Boolean> script;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void transfer(String fromAccount, String toAccount, int amount) {
        Boolean backInfo = this.redisTemplate
                .execute(script, Arrays.asList(fromAccount, toAccount), String.valueOf(amount));

        logger.info("------> transfer backInfo :{} <-------", backInfo);

    }

}
