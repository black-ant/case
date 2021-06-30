package com.gang.redis.lua.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.RedisScript;

import java.lang.reflect.Array;

/**
 * @Classname RedisScriptConfig
 * @Description TODO
 * @Date 2021/6/29
 * @Created by zengzg
 */
@Configuration
public class RedisScriptConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public RedisScript<Boolean> scriptBoolean() {

        logger.info("------> config 加载 Boolean 返回脚本 <-------");

        // 导入 moneyTransfer 计算参数
        Resource scriptSource = new ClassPathResource("scripts/moneyTransfer.lua");
        return RedisScript.of(scriptSource, Boolean.class);
    }


    @Bean
    public RedisScript<String[]> scriptArray() {

        logger.info("------> config 加载 Array 返回脚本 <-------");
        // 导入限流 lua
        Resource scriptSource = new ClassPathResource("scripts/currentLimiting.lua");
        return RedisScript.of(scriptSource, String[].class);
    }

    @Bean
    public RedisScript<String> scriptStr() {

        logger.info("------> config 加载 Array 返回脚本 <-------");
        // 导入限流 lua
        Resource scriptSource = new ClassPathResource("scripts/backStrInfo.lua");
        return RedisScript.of(scriptSource, String.class);
    }

}
