package com.gang.study.spring.ssh.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname HelloController
 * @Description TODO
 * @Date 2020/6/8 13:38
 * @Created by zengzg
 */
@RequestMapping("/test")
@RestController
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/hello")
    public String hello() {
        logger.info("------> this is in hello <-------");
        return "hello";
    }
}
