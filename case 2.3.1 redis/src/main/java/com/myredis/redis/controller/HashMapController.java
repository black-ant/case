package com.myredis.redis.controller;

import com.myredis.redis.entity.User;
import com.myredis.redis.util.RedisSetUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname HashMapController
 * @Description TODO
 * @Date 2020/1/8 11:50
 * @Created by zengzg
 */
@RestController
@RequestMapping("/hash")
public class HashMapController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //    @Autowired
    RedisSetUtils redisSetUtils;

    @GetMapping("/set/{key}")
    public void set(@PathVariable("key") String key) {
        logger.info("------> this is in get Map<-------");
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "111");
        map.put("2", "222");
        map.put("3", "333");
        redisSetUtils.hsetMap(key, map);
    }

    @GetMapping("/setobj/{key}")
    public void setObject(@PathVariable("key") String key) {
        logger.info("------> this is in get Map<-------");
        HashMap<String, User> map = new HashMap<>();
        map.put("1", new User("gang", new Date(), Double.valueOf(333), Long.valueOf(666)));
        redisSetUtils.hsetMap(key, map);
    }

    @GetMapping("/getobj/{key}")
    public void getObject(@PathVariable("key") String key) {
        logger.info("------> this is in get Map<-------");
        Map<String, User> map = redisSetUtils.hgetMap(key);
        logger.info("------> this is :{} <-------", map.get("1"));
        logger.info("------> this is instanceof :{} <-------", map.get("1") instanceof User);
    }

    @GetMapping("/get/{key}/{field}")
    public void get(@PathVariable("key") String key, @PathVariable("field") String field) {
        logger.info("------> this is in get :{} --{}<-------", key, field);
        redisSetUtils.hget(key, field);
    }

    @GetMapping("/set/{key}/{filed}/{value}")
    public String set(@PathVariable("key") String key, @PathVariable("filed") String field,
                      @PathVariable("value") String value) {
        logger.info("------> this is in :{} --{} -{}  <-------", key, field, value);
        redisSetUtils.hset(key, field, value);
        return "set ok " + key + "-" + field + "-" + value;
    }
}
