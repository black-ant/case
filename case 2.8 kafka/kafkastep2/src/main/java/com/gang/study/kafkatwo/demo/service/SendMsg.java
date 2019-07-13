package com.mykafka.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/25 17:16
 * @Version 1.0
 **/
@Service
public class SendMsg {

    @Value("${spring.kafka.topic.Name}")
    private String topicName;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        long start = System.currentTimeMillis();
        kafkaTemplate.send(topicName, msg);
        long end = System.currentTimeMillis();
    }

    public void sendMessageHavaTopic(String topicname, String msg) {
        long start = System.currentTimeMillis();
        kafkaTemplate.send(topicname, msg);
        long end = System.currentTimeMillis();
    }


}
