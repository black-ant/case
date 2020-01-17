package com.gang.kafka.exception.kafkaexceptionback.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsume {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @KafkaListener(topics = "start", groupId = "ant")
    public void Listener(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in listerner :{}<-------", record.value());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @KafkaListener(topics = "start", groupId = "ant1")
    public void Listener2(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in listerner222222222 :{}<-------", record.value());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
