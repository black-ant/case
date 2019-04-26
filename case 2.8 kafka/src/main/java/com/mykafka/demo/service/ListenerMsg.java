package com.mykafka.demo.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/25 17:20
 * @Version 1.0
 **/
@Service
public class ListenerMsg {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CountDownLatch latch1 = new CountDownLatch(1);

    @KafkaListener(id = "foo", topics = "annotated1")
    public void listen1(String foo) {
        this.latch1.countDown();
    }

    @KafkaListener(topics = "${spring.kafka.topic.Name}")
    private void consumer(String record) {
        logger.info("msg is :{}", JSON.toJSONString(record));
    }

    @KafkaListener(topics = "gid001")
    private void consumerHavaTopic(String record) {
        logger.info("msg is :{}", JSON.toJSONString(record));
    }
}
