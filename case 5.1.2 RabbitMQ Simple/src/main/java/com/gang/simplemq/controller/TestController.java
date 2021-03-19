package com.gang.simplemq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname TestController
 * @Description TODO
 * @Created by zengzg
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    @GetMapping("send/{routing}/{msg}")
    public String send(@PathVariable("msg") String msg, @PathVariable("routing") String routing) {
        //        logger.info("------> 发送基本消息 <-------");
        //        rabbitTemplate.convertAndSend("myQueue", "测试发送消息 myQueue ");

        logger.info("------> 发送带exchange 消息 <-------");
        rabbitTemplate.convertAndSend("myExchange", routing, "测试 : exchange + queue" + msg);

        //        logger.info("------> 发送带key消息 <-------");
        //        rabbitTemplate.convertAndSend("myExchange", "myQueue", "测试 : exchange + queue");

        return "ok";
    }

    @GetMapping("sendExchange/{routing}/{msg}")
    public String sendExchange(@PathVariable("msg") String msg) {
        //        logger.info("------> 发送基本消息 <-------");
        //        rabbitTemplate.convertAndSend("myQueue", "测试发送消息 myQueue ");

//        logger.info("------> 发送带exchange 消息 <-------");
//        rabbitTemplate.convertAndSend("myExchange", routing, "测试 : exchange + queue" + msg);

        //        logger.info("------> 发送带key消息 <-------");
        //        rabbitTemplate.convertAndSend("myExchange", "myQueue", "测试 : exchange + queue");

        return "ok";
    }

    @GetMapping("sends")
    public String sendMany() {

        logger.info("------> 发送带exchange 消息 <-------");

        String routing = "thread";
        Integer num = 0;

        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                rabbitTemplate.convertAndSend("myExchange", routing,
                        "测试 : exchange + queue : " + new Random().nextInt(99999));
                Thread.sleep(500);
                return null;
            });
        }
        return "success";
    }
}
