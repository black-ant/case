package com.gang.kafkaone.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaStartController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/test/send")
    public void testSend() {

    }
}
