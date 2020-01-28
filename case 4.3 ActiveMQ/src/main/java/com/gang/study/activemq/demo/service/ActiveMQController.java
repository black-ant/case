package com.gang.study.activemq.demo.service;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;

/**
 * @Classname ActiveMQController
 * @Description TODO
 * @Date 2020/1/28 23:02
 * @Created by zengzg
 */
@RestController
@RequestMapping("/test")
public class ActiveMQController {

    @Autowired
    private Producer producer;

    @ResponseBody
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public Object send() {
        Destination destination = new ActiveMQQueue("mytest.queue");
        producer.send(destination, "I am YeJiaWei");
        return "OK";
    }
}
