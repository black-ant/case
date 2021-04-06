package com.gang.simplemq.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @Classname TestDirectService
 * @Description 把消息路由到那些binding key与routing key完全匹配的Queue
 * @Date 2021/3/29
 * @Created by zengzg
 */
@Component
public class TestDirectService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("DirectA"),
            key = "ONE",
            exchange = @Exchange(name = "DirectExchange", type = ExchangeTypes.DIRECT)
    ))
    @SendTo("TWO")
    public void processA(String message) {
        logger.info("------> DirectA 发送接收成功  :{}<-------", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("HeaderA"),
            key = "ONE",
            exchange = @Exchange(name = "TestExchange", type = ExchangeTypes.DIRECT)
    ))
    public void processC(String message) {
        logger.info("------> HeaderA 发送接收成功  :{}<-------", message);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("DirectB"),
            key = "TWO",
            exchange = @Exchange(name = "DirectExchange", type = ExchangeTypes.DIRECT)
    ))
    public void processB(String message) {
        logger.info("------> DirectB 发送接收成功  :{}<-------", message);
    }
}
