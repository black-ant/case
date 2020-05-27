package com.gang.spring.interceptors.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/5/27 19:15
 * @Created by zengzg
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/levelOne")
    public void levelOne() {
        logger.info("------> this is levelOne <-------");
    }

    @GetMapping("/levelTwo/one")
    public void levelTwo1() {
        logger.info("------> this is levelTwo1 <-------");
    }

    @GetMapping("/levelTwo/one/1")
    public void levelTwo12() {
        logger.info("------> this is levelTwo12 <-------");
    }

    @GetMapping("/levelTwo/two")
    public void levelTwo2() {
        logger.info("------> this is  levelTwo2<-------");
    }

    @GetMapping("/levelThree/two/2")
    public void leveThree2() {
        logger.info("------> this is  levelTwo2<-------");
    }
}
