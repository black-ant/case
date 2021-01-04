package com.gang.study.activemq.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * @Classname Producer
 * @Description TODO
 * @Date 2020/1/28 23:01
 * @Created by zengzg
 */
@Component
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(Destination destination, final String message) {
        jmsMessagingTemplate.convertAndSend(destination, message + "from queue");
    }

    @JmsListener(destination = "out.queue")
    public void consumerMessage(String text) {
        System.out.println("从out.queue队列收到的回复信息为:" + text);
    }
}

