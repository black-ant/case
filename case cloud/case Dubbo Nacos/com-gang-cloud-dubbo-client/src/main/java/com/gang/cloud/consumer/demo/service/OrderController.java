package com.gang.cloud.consumer.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname OrderController
 * @Description TODO
 * @Date 2020/8/16 12:12
 * @Created by zengzg
 */
@RestController
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @ResponseBody   //以json格式返回
    @RequestMapping("/initOrder")
    public String initOrder(@RequestParam("uid") String userId) {
        return orderService.initOrder(userId);
    }

}
