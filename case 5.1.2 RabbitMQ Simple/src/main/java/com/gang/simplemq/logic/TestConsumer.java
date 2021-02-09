package com.gang.simplemq.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname TestConsumer
 * @Description TODO
 * @Date 2020/3/1 16:27
 * @Created by zengzg
 */
@Component
public class TestConsumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 需手动创建队列 不然启动报错
     *
     * @param message
     */
    @RabbitListener(queues = "myQueue")
    public void process(String message) {
        logger.info("------> queue 发送接收成功  :{}<-------", message);
    }

    /**
     * 自动创建队列
     *
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    public void processByDeclare(String message) {
        logger.info("------> 自动创建 queue 发送接收成功   :{}<-------", message);
    }

    /**
     *
     * 自动创建队列
     *
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    public void listenExchange(String message) {
        logger.info("------> 自动创建 queue 发送接收成功   :{}<-------", message);
    }


    /**
     * 绑定Exchange
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueueex"),
            key = "thread",
            exchange = @Exchange("myExchange")
    ))
    public void processBindExchange(String message) {
        logger.info("------> processBindExchange 接收成功 :{} <-------", message);
    }

    /**
     * 根据key接收消息
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueuekey"),
            key = "apple",
            exchange = @Exchange("myExchange")
    ))
    public void processApple(String message) {
        System.out.println("apple" + message);
    }

    /**
     * 根据key接收消息
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueuekey"),
            key = "fruit",
            exchange = @Exchange("myExchange")
    ))
    public void processFruit(String message) {
        System.out.println("fruit" + message);
    }

}
