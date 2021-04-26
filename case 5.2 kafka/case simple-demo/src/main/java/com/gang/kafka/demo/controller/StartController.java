package com.gang.kafka.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2021/4/25
 * @Created by zengzg
 */
@RestController
@RequestMapping("/start")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("produce")
    public String produceMsg(@RequestParam(name = "id", defaultValue = "1") String id) {
        logger.info("------> produce one msg <-------");
        kafkaTemplate.send("start", "one", "are you ok?" + "----" + id);
        return "";
    }
}
