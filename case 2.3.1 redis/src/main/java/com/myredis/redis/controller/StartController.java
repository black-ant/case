package com.myredis.redis.controller;

import com.alibaba.fastjson.JSONObject;
import com.myredis.redis.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/26 16:33
 * @Version 1.0
 **/
@RestController
@RequestMapping("/")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TestService testService;

    @GetMapping("/test")
    public void test() {
        logger.info("------> this is in <-------");
    }

    @GetMapping("/testSet")
    public String testSet() {
        logger.info("------> this is in <-------");
        return "ok";
    }

    @GetMapping(value = "get")
    public String findOneCity(@RequestParam("id") String id, @RequestParam("type") String type) {
        logger.info("find by key :{}", id);
        switch (type) {
            case "1":
                return JSONObject.toJSONString(testService.findByKey(id));
            case "2":
                return JSONObject.toJSONString(testService.findByKeyTemp1(id));
            case "3":
                return JSONObject.toJSONString(testService.findByKeyTemp2(id));
            default:
                return "no one";
        }

    }

    @GetMapping(value = "set")
    public String setOneKey(@RequestParam("id") String id, @RequestParam("data") String data, @RequestParam("type") String type) {
        logger.info("setOneKey by key :{},{}", id, data);
        switch (type) {
            case "1":
                return JSONObject.toJSONString(testService.saveByKey(id, data));
            case "2":
                return JSONObject.toJSONString(testService.saveByKeyTemp1(id, data));
            case "3":
                return JSONObject.toJSONString(testService.saveByKeyTemp2(id, data));
            default:
                return "no one";
        }

    }
}
