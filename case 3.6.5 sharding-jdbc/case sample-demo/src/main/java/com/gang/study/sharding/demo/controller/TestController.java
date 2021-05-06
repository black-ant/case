package com.gang.study.sharding.demo.controller;

import com.gang.study.sharding.demo.service.TestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/5/5
 * @Created by zengzg
 */
@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TestServiceImpl testService;

    @RequestMapping("/test/insert")
    public void test3(HttpServletRequest request) {
        logger.info("------> /test/insert <-------");
        testService.test3();
    }

    @RequestMapping("/test/select")
    public void select(HttpServletRequest request) {
        logger.info("------> /test/select <-------");
        testService.select();
    }
}
