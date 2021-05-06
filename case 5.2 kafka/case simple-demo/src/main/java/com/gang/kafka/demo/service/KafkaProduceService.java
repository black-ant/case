package com.gang.kafka.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

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
//        for (int i = 0; i < 5; i++) {
////            logger.info("------> produce :{} <-------", i);
////            kafkaTemplate.send("start", "one", "are you ok?" + "----" + i);
////        }
        logger.info("------> produce one two <-------");
        ListenableFuture future = kafkaTemplate.send("start", "one", "are you ok one?" + "----");
        try {
            SendResult result = (SendResult) future.get();
            logger.info("------> result :{} <-------", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        kafkaTemplate.send("topic1", "two", "are you ok two?" + "----");
    }
}
