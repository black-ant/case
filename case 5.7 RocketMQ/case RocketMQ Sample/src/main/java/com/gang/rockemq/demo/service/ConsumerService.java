package com.gang.rockemq.demo.service;

import com.gang.rockemq.demo.to.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Classname ConsumerService
 * @Description TODO
 * @Date 2021/8/31
 * @Created by zengzg
 */
@Service
public class ConsumerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Service
    @RocketMQMessageListener(topic = "topic-queue-one", consumerGroup = "consumer_topic-queue-one")
    public class ConsumerOne implements RocketMQListener<Message> {
        @Override
        public void onMessage(Message message) {
            logger.info("consumer-one received message: {}", message);
        }
    }

    @Service
    @RocketMQMessageListener(topic = "topic-queue-two", consumerGroup = "consumer_topic-queue-two")
    public class ConsumerTwo implements RocketMQListener<String> {
        @Override
        public void onMessage(String message) {
            logger.info("consumer-two received message: {}", message);
        }
    }
}
