package com.myredis.redis.controller;

import com.alibaba.fastjson.JSONObject;
import com.myredis.redis.service.TestService;
import com.myredis.redis.service.UseUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/27 19:49
 * @Version 1.0
 **/
@RestController
@RequestMapping("util")
public class UtilController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UseUtilService useUtilService;

    @GetMapping(value = "get")
    public String findOneCity(@RequestParam("id") String id, @RequestParam("type") String type) {
        logger.info("find by key :{}", id);
        switch (type) {
            case "1":
                return JSONObject.toJSONString(useUtilService.getByKey(id));
            default:
                return "no one";
        }

    }

    @GetMapping(value = "set")
    public String setOneKey(@RequestParam("id") String id, @RequestParam("data") String data, @RequestParam("type") String type) {
        logger.info("setOneKey by key :{},{}", id, data);
        switch (type) {
            case "1":
                return JSONObject.toJSONString(useUtilService.saveByKey(id, data));
            case "2":
                return JSONObject.toJSONString(useUtilService.useCallback(id, data));
            default:
                return "no one";
        }

    }
}
