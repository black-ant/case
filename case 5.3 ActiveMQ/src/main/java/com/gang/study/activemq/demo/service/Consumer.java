package com.gang.study.activemq.demo.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @Classname Consumer
 * @Description TODO
 * @Date 2020/1/28 23:01
 * @Created by zengzg
 */

@Component
public class Consumer {

    @JmsListener(destination = "mytest.queue")
    @SendTo("out.queue")
    public String receiveQueue(String text) {
        System.out.println("Consumer收到的信息为:" + text);
        return "return message " + text;
    }
}
