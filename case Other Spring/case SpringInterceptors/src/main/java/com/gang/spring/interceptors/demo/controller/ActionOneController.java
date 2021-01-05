package com.gang.spring.interceptors.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname OneActionController
 * @Description TODO
 * @Date 2020/6/24 15:15
 * @Created by zengzg
 */
@RestController
@RequestMapping("/one")
public class ActionOneController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String test() {
        logger.info("------> this is in One test <-------");
        return "One ok";
    }
}
