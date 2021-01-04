package com.chapterJPA.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.chapterJPA.demo.service.RedisService;
import com.chapterJPA.demo.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    RedisService redisService;
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/getredis")
    public JSONObject findByRedis(@RequestParam("key") String key){
        JSONObject node = new JSONObject();
        node.put("value",redisUtil.get(key));
        node.put("status",true);
        return node;
    }

    @RequestMapping("/setredis")
    public JSONObject setRedisValue(){
        JSONObject node = new JSONObject();
        redisUtil.set("one","this is one key");
        node.put("status",true);
        return node;
    }
}
