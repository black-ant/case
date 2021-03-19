package com.gang.kafka.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Classname KafkaConsumerService
 * @Description TODO
 * @Date 2021/3/19
 * @Created by zengzg
 */
@Component
public class KafkaConsumerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void init() {
        logger.info("------> KafkaConsumerService 已经初始化 <-------");
    }

    @KafkaListener(id = "one", topics = "start", clientIdPrefix = "myClientId")
    public void listener0(ConsumerRecord<?, ?> record) {
        logger.info("------> this is in listerner 0:{}<-------", record.value());
    }

}
