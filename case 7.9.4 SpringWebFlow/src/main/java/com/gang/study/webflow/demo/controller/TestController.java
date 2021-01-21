package com.gang.study.webflow.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/1/21 17:26
 * @Created by zengzg
 */
@RequestMapping("/test")
@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/get")
    public String getTestInfo() {
        logger.info("------> this is in test <-------");
        return "this is success test";
    }
}
