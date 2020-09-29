package com.gang.spring.beanmanager.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/9/29 15:24
 * @Created by zengzg
 */
@RestController
@RequestMapping("/start")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("one")
    public String one() {
        logger.info("------> this is in start One <-------");
        return "success";
    }

}
