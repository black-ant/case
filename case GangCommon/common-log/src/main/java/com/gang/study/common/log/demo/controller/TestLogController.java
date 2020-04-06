package com.gang.study.common.log.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestLogController
 * @Description TODO
 * @Date 2020/4/5 13:38
 * @Created by zengzg
 */
@RestController
@RequestMapping("/log")
public class TestLogController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("add")
    public String addLog() {
        logger.info("------> this is log <-------");
        logger.error("------> this is error");
        return "ok";
    }
}
