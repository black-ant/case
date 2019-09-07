package com.gang.study.myfilter.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/test")
    public String getTest() {
        logger.info("------> this is test <-------");
        return "test ok";
    }
}
