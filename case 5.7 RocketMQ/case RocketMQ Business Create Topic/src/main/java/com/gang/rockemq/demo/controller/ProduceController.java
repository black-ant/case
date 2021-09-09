package com.gang.rockemq.demo.controller;

import com.gang.rockemq.demo.to.OrderTO;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @Classname ProduceController
 * @Description TODO
 * @Date 2021/9/8
 * @Created by zengzg
 */
@RequestMapping("produce")
@RestController
public class ProduceController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("/set")
    public String produce() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                OrderTO orderTO = new OrderTO();
                orderTO.setOrderId("ant-" + new Random().nextInt(99999999));
                rocketMQTemplate.convertAndSend("ant-topic-" + j, orderTO);
            }
        }
        return "success";
    }
}
