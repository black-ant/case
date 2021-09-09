package com.gang.rockemq.demo.controller;

import com.alibaba.fastjson.JSON;
import com.gang.rockemq.demo.to.OrderTO;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/9/8
 * @Created by zengzg
 */
@RequestMapping("test")
@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * @return
     */
    @RequestMapping("/set")
    public String produce() {

        logger.info("------> 测试 Template 发送消息 <-------");
        OrderTO orderTO = new OrderTO();
        orderTO.setOrderId("123456");
        rocketMQTemplate.convertAndSend("ant-topic", orderTO);
        return "success";
    }

}
