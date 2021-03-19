package com.gang.simplemq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname HighTestController
 * @Description TODO
 * @Created by zengzg
 */
@RequestMapping("high")
@RestController
public class HighTestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 生产不推荐 , 本地测试使用
     */
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("produce")
    public String produce() {

        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                String msg = "[Thread Pool Produce]-[data : 数据ID " + new Random().nextInt(9999999) + "]";
                rabbitTemplate.convertAndSend("myExchange", "thread", "测试 : exchange + queue" + msg);
                return null;
            });
        }
        return "success";
    }
}
