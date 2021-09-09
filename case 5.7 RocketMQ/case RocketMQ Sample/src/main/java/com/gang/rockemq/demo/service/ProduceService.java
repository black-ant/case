package com.gang.rockemq.demo.service;

import com.gang.rockemq.demo.to.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Classname ProduceService
 * @Description TODO
 * @Date 2021/8/31
 * @Created by zengzg
 */
@Service
public class ProduceService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    public ProduceService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public void start() {
        logger.info("------> Produce 启动 <-------");
        Message<String> message = new Message<>();
        message.setId(UUID.randomUUID().toString());
        message.setContent("Hello, springboot-ac-rocketmq !");
        rocketMQTemplate.convertAndSend("topic-queue-one", message);
        rocketMQTemplate.convertAndSend("topic-queue-two", "Hello, springboot-ac-rocketmq !");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            start();
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            e.printStackTrace();
        }
    }
}
