package com.gang.simplemq.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Classname TemplateFactoryController
 * @Description TODO
 * @Date 2021/4/5
 * @Created by zengzg
 */
public class TemplateFactoryController {


    public void test(){
        RabbitTemplate template = new RabbitTemplate();
        template.setRoutingKey("queue.helloWorld");
//        template.setConfirmCallback();
        MessageProperties properties = new MessageProperties();
        //...

        template.send(new Message("Hello World".getBytes(), properties));
    }
}
