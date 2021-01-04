package com.gang.kafkaone.demo.service;

import com.gang.kafkaone.demo.entity.MsgOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(id = "fooGroup", topics = "topic_1")
    public void listen(MsgOne msg) {
        logger.info("========== Received: " + msg);
        if (msg.getMsg().startsWith("fail")) {
            throw new RuntimeException("failed");
        }
    }

    @KafkaListener(id = "dltGroup", topics = "topic_1.DLT")
    public void dltListen(String in) {
        logger.info("Received from DLT: " + in);
    }
}
