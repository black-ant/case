package com.gang.simplemq.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname TestTopicService
 * @Description TODO
 * @Date 2021/3/29
 * @Created by zengzg
 */
@Component
public class TestTopicService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("TopicA"),
            key = "ONE.*",
            exchange = @Exchange(name = "TopicExchange", type = ExchangeTypes.TOPIC)
    ))
    public void processA(String message) {
        logger.info("------> TopicA 发送接收成功  :{}<-------", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("TopicB"),
            key = "TWO",
            exchange = @Exchange(name = "TopicExchange", type = ExchangeTypes.TOPIC)
    ))
    public void processB(String message) {
        logger.info("------> TopicB 发送接收成功  :{}<-------", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("TopicA"),
            key = "Three*",
            exchange = @Exchange(name = "TopicExchange", type = ExchangeTypes.TOPIC)
    ))
    public void processC(String message) {
        logger.info("------> TopicA 发送接收成功  :{}<-------", message);
    }
}
