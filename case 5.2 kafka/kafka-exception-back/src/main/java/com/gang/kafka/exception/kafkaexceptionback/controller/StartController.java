package com.gang.kafka.exception.kafkaexceptionback.controller;

import com.gang.kafka.exception.kafkaexceptionback.service.KafkaProduce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaProduce produce;

    @GetMapping("/start")
    public String sendOk() throws InterruptedException {
        logger.info("------> -------okkkkk <-------");
//        produce.send();
        return "is ok " + new Date();
    }
}
