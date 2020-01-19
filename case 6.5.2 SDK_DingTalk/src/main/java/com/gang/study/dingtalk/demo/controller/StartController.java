package com.gang.study.dingtalk.demo.controller;

import com.gang.study.dingtalk.demo.service.DingTalkToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/1/19 10:20
 * @Created by zengzg
 */
@RestController
@RequestMapping("/start")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DingTalkToken dingTalkToken;

    @GetMapping("/get")
    public String get() {
        logger.info("------> this is in get <-------");
        return "that's ok";
    }

    @GetMapping("/getToken")
    public String getToken() {
        logger.info("------> this in get Token <-------");
        return dingTalkToken.getToken();
    }
}
