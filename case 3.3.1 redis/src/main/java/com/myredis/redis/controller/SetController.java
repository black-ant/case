package com.myredis.redis.controller;

import com.myredis.redis.util.RedisSetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SetController
 * @Description TODO
 * @Date 2020/1/8 14:53
 * @Created by zengzg
 */
@RestController
@RequestMapping("/set")
public class SetController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //    @Autowired
    RedisSetUtils redisSetUtils;

    @GetMapping("/get/{key}")
    public String get(@PathVariable("key") String key) {
        logger.info("------> this is in get :{} <-------", key);
        return redisSetUtils.setGet(key);
    }

    @GetMapping("/set/{key}/{value}")
    public String set(@PathVariable("key") String key, @PathVariable("value") String value) {
        logger.info("------> this is in :{}  -{}  <-------", key, value);
        redisSetUtils.setSet(key, value);
        return "set ok " + key + "-" + "-" + value;
    }
}
