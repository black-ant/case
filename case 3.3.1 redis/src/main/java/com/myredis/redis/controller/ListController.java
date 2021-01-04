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
 * @Classname ListController
 * @Description TODO
 * @Date 2020/1/8 13:51
 * @Created by zengzg
 */
@RestController
@RequestMapping("/list")
public class ListController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //    @Autowired
    RedisSetUtils redisSetUtils;

    @GetMapping("/get/{key}/{field}")
    public void get(@PathVariable("key") String key, @PathVariable("field") Long field) {
        logger.info("------> this is in get :{} --{}<-------", key, field);
        redisSetUtils.listGet(key, field);
    }

    @GetMapping("/set/{key}/{filed}/{value}")
    public String set(@PathVariable("key") String key, @PathVariable("filed") Long field,
                      @PathVariable("value") String value) {
        logger.info("------> this is in :{} --{} -{}  <-------", key, field, value);
        redisSetUtils.listSet(key, field, value);
        return "set ok " + key + "-" + field + "-" + value;
    }

    @GetMapping("/set/{key}/{value}")
    public String setPush(@PathVariable("key") String key, @PathVariable("value") String value) {
        logger.info("------> this is in :{} -{}  <-------", key, value);
        redisSetUtils.listPush(key, value);
        return "set ok " + key + "-" + value;
    }
}
