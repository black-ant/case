package com.gang.study.taskkafka.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Classname KafkaProduce
 * @Description TODO
 * @Date 2019/11/1 13:58
 * @Created by ant-black 1016930479@qq.com
 */
@Service
public class KafkaProduce {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void produce() {
        for (int i = 0; i < 5; i++) {
            logger.info("------> produce :{} <-------", i);
//            kafkaTemplate.send("start", "are you ok?" + "----" + i);
//            kafkaTemplate.send("test", "are you ok?" + "----" + i);
            kafkaTemplate.send("start", "one", "are you ok?" + "----" + i);
//            kafkaTemplate.send("test", 1, "1", "are you ok?" + "----" + i);
        }
    }
}
