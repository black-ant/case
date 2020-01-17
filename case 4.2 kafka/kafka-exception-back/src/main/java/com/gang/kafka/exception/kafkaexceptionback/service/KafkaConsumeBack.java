package com.gang.kafka.exception.kafkaexceptionback.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumeBack {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //    @KafkaListener(topics = "start", groupId = "ant")
//    @SendTo("error")
    public String Listener(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in listerner :{}<-------", record.value());
        return "ok";
    }

    @KafkaListener(topics = "start", groupId = "ant")
    public String Listener1(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in error :{}<-------", record.value());
        return "ok";
    }


}
