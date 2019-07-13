package com.gang.kafkaone.demo.service;


import com.gang.kafkaone.demo.entity.MsgOne;
import com.gang.kafkaone.demo.entity.MsgTwo;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;

@KafkaListener(id = "listenerClass", topics = { "topic_1", "topic_2" })
public class KafkaListenerClass {

    @KafkaHandler
    public void msgOne(MsgOne foo) {
        System.out.println("MsgOne  Received: " + foo);
    }

    @KafkaHandler
    public void msgTwo(MsgTwo bar) {
        System.out.println("MsgTwo  Received: " + bar);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("Received unknown: " + object);
    }
}
