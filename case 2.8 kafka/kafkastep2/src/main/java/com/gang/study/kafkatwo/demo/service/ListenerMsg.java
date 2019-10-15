package com.gang.study.kafkatwo.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;
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
        logger.info("msg111 is :{}", JSON.toJSONString(record));
    }

    @KafkaListener(topics = "gid001")
    private void consumerHavaTopic(String record) {
        logger.info("msg222 is :{}", JSON.toJSONString(record));
    }

    @KafkaListener(topics = "all001", errorHandler = "errorHandleMethod")
    public void listen_8(List<ConsumerRecord<?, ?>> data, Consumer<?, ?> consumer) {
        logger.info("------> data is :{} <-------", JSONObject.toJSONString(data));
        logger.info("------> consumer is :{} <-------", JSONObject.toJSONString(consumer));
    }
}
