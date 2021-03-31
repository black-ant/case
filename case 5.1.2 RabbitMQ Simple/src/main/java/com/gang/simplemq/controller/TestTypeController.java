package com.gang.simplemq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestTypeController
 * @Description TODO
 * @Date 2021/3/29
 * @Created by zengzg
 */
@RestController
@RequestMapping("/type")
public class TestTypeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("direct")
    public String getDirect(@RequestParam("msg") String msg) throws Exception {

        logger.info("------> direct type 测试发送开始 -- 空字符串 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("DirectExchange", "", "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);

        logger.info("------> direct type 测试发送开始 -- NULL :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("DirectExchange", null, "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);

        logger.info("------> direct type 测试发送 Routing key 开始 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("DirectExchange", "ONE", "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);

        logger.info("------> direct type 测试发送 No Exchange  开始 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("ONE", "发送消息 :" + msg);

        return "success";
    }

    /**
     * 不论 是否传入 Routing Key , 对结果无影响
     *
     * @param msg
     * @return
     */
    @GetMapping("fanout")
    public String get(@RequestParam("msg") String msg) throws Exception {

        logger.info("------> fanout type 测试发送开始 -- 空字符串 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("FanoutExchange", "", "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);

        logger.info("------> fanout type 测试发送开始 -- NULL :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("FanoutExchange", null, "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);

        logger.info("------> fanout type 测试发送 Routing key 开始 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("FanoutExchange", "ONE", "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);

        logger.info("------> fanout type 测试发送 No Exchange  开始 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("ONE", "发送消息 :" + msg);

        return "success";
    }


    /**
     * 不论 是否传入 Routing Key , 对结果无影响
     *
     * @param msg
     * @return
     */
    @GetMapping("topic")
    public String getTopic(@RequestParam("msg") String msg) throws Exception {

        logger.info("------> topic type 测试发送开始 -- 空字符串 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("TopicExchange", "", "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);

        logger.info("------> topic type 测试发送开始 -- NULL :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("TopicExchange", null, "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);

        logger.info("------> topic type 测试发送 Routing key 开始 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("TopicExchange", "ONE", "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);

        logger.info("------> topic type 测试发送 Routing ONE.AAA 开始 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("TopicExchange", "ONE.AAA", "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);

        logger.info("------> topic type 测试发送 Routing ONE.TWO 开始 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("TopicExchange", "ONE.TWO", "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);

        logger.info("------> topic type 测试发送 Routing ONEAAA 开始 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("TopicExchange", "ONEAAA", "发送消息 :" + msg);
        Thread.sleep(500);
        logger.info("------> ----- <-------", msg);


        logger.info("------> fanout type 测试发送 No Exchange  开始 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("ONE", "发送消息 :" + msg);

        return "success";
    }

    @GetMapping("header")
    public String getHeader(@RequestParam("msg") String msg) throws Exception {
        logger.info("------> fanout type 测试发送 No Exchange  开始 :[{}] <-------", msg);
        rabbitTemplate.convertAndSend("ONE", "发送消息 :" + msg);

        return "success";
    }


}
