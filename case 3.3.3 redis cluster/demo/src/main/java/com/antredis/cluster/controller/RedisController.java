package com.antredis.cluster.controller;

import com.alibaba.fastjson.JSONObject;
import com.antredis.cluster.service.CacheService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/26 11:50
 * @Version 1.0
 **/
@RestController
public class RedisController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CacheService cacheService;

    @GetMapping("save")
    public String SaveLogisticsOrder(@RequestParam("type") String type, @RequestParam("data") String orderData) {
        logger.info("controller save data is :{}", orderData);
        cacheService.saveCache(type, orderData);
        return "ok";
    }

    @GetMapping("get")
    public String getByKey(@RequestParam("key") String key) {
        logger.info("controller get data is :{}", key);
        return JSONObject.toJSONString(cacheService.getCache(key));
    }

//    @GetMapping("getOrderList")
//    public List<Object> getOrderList(@RequestParam("type") String type) {
////        return orderCacheService.findLogisticsOrderList(type);
//        return cacheBack(orderCacheService.findLogisticsOrderListDBO());
//    }
}


