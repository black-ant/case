package com.mybatistest.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mybatistest.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/20 18:07
 * @Version 1.0
 **/
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("findByOrderName")
    public String findByOrderName(@RequestParam("name") String name) {
        return JSONObject.toJSONString(orderService.findByOrderName(name));
    }

    @GetMapping("findall")
    public String findAll() {
        return JSONObject.toJSONString(orderService.findAll());
    }
}
