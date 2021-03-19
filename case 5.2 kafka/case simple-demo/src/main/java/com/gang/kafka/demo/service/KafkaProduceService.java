package com.gang.kafka.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Classname KafkaProduceService
 * @Description TODO
 * @Date 2021/3/19
 * @Created by zengzg
 */
@Component
public class KafkaProduceService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void produce() {
        for (int i = 0; i < 5; i++) {
            logger.info("------> produce :{} <-------", i);
            kafkaTemplate.send("start", "one", "are you ok?" + "----" + i);
        }
    }
}
