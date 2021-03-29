package com.gang.simplemq.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname TestHeaderService
 * @Description TODO
 * @Date 2021/3/29
 * @Created by zengzg
 */
@Component
public class TestHeaderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("FanoutA"),
            key = "ONE",
            exchange = @Exchange(name = "FanoutExchange", type = ExchangeTypes.HEADERS),
            arguments = {
                    @Argument(name = "headerValue", value = "AntBlack")
            }
    ))
    public void processA(String message) {
        logger.info("------> FanoutA 发送接收成功  :{}<-------", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "HeaderB", autoDelete = "true",
                    arguments = @Argument(name = "x-message-ttl", value = "10000",
                            type = "java.lang.Integer")),
            exchange = @Exchange(value = "HeaderExchange", type = ExchangeTypes.HEADERS, autoDelete = "true"),
            arguments = {
                    @Argument(name = "x-match", value = "all"),
                    @Argument(name = "thing1", value = "somevalue"),
                    @Argument(name = "thing2")
            })
    )
    public void processB(String message) {
        logger.info("------> FanoutB 发送接收成功  :{}<-------", message);
    }
}
