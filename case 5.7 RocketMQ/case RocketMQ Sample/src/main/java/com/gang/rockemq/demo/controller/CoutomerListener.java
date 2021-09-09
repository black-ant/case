package com.gang.rockemq.demo.controller;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname RocketMQListener
 * @Description TODO
 * @Date 2021/9/8
 * @Created by zengzg
 */
@RocketMQMessageListener(topic = "ant-topic", consumerGroup = "black-group")
@Component
public class CoutomerListener implements RocketMQListener<String> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(String s) {
        logger.info("------> listener MSG :[{}] <-------", s);
    }
}
