package com.gang.zuul.business.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2021/6/30
 * @Created by zengzg
 */
@RequestMapping("/start")
@RestController
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/get")
    public String get() {
        logger.info("------> Start Controller <-------");
        return "get success";
    }
}
