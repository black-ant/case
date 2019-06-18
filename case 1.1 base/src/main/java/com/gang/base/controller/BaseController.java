package com.gang.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("callBack")
    public void callBack(HttpRequest request) {
        logger.info("this is :{}", request.toString());
    }

    @GetMapping("test")
    public void test() {
        logger.info("this is ok");
    }
}
