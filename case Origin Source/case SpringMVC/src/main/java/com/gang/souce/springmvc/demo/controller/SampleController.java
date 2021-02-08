package com.gang.souce.springmvc.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SampleController
 * @Description TODO
 * @Date 2021/2/8 22:39
 * @Created by zengzg
 */
@RestController
@RequestMapping("sample")
public class SampleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("get")
    public String get() {
        logger.info("------> this is in get <-------");
        return "getSuccess";
    }

    @GetMapping("post")
    public String post() {
        logger.info("------> this is in post <-------");
        return "postSuccess";
    }
}
