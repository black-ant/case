package com.gang.simplemq.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname TestProduce
 * @Description TODO
 * @Date 2020/3/1 16:07
 * @Created by zengzg
 */
@Component
public class TestProduce implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is run <-------");
//        send();
    }

    public void send() {
        rabbitTemplate.convertAndSend("message", "测试发送消息");
    }
}

