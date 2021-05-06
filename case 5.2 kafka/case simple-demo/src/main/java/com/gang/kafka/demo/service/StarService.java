package com.gang.kafka.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Classname StartApplication
 * @Description TODO
 * @Date 2021/3/19
 * @Created by zengzg
 */
@Component
public class StarService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 先调用初始化
     */
    @Autowired
    private KafkaConsumerService consumerService;

    @Autowired
    private KafkaProduceService produceService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        consumerService.init();
//        produceMsg();
    }

    /**
     * 生产数据
     */
    @Scheduled(fixedDelay = 30000, initialDelay = 5000)
    public void produceMsg() {
        logger.info("------> [生产数据 ] <-------");
        produceService.produce();
    }
}
