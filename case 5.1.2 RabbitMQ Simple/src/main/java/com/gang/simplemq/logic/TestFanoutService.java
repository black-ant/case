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
 * @Classname TestFanoutService
 * @Description TODO
 * @Date 2021/3/29
 * @Created by zengzg
 */
@Component
public class TestFanoutService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("FanoutA"),
            key = "ONE",
            exchange = @Exchange(name = "FanoutExchange", type = ExchangeTypes.FANOUT)
    ))
    public void processA(String message) {
        logger.info("------> FanoutA 发送接收成功  :{}<-------", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("FanoutB"),
            key = "TWO",
            exchange = @Exchange(name = "FanoutExchange", type = ExchangeTypes.FANOUT)
    ))
    public void processB(String message) {
        logger.info("------> FanoutB 发送接收成功  :{}<-------", message);
    }
}
