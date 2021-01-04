package com.mqproject.demo.handler;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/22 11:06
 * @Version 1.0
 **/
@Component
public class MQProduce {
    /**
     * 此处使用RabbitTemplate
     */
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MQProduce(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String ROUTINGKEY_A = "my-mq-routingKey_A";

    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(EXCHANGE_A, ROUTINGKEY_A, content, correlationId);
    }
}
