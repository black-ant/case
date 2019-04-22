package com.test.thread.controller;

import com.test.thread.service.ComplexThread;
import com.test.thread.service.MediumThread;
import com.test.thread.service.SimpleThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/22 23:10
 * @Version 1.0
 **/
@RestController
public class startController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SimpleThread simpleThread;
    @Autowired
    MediumThread mediumThread;
    @Autowired
    ComplexThread complexThread;

    @GetMapping("example")
    public String myThreadExample(@RequestParam("type") String type) {
        logger.info("this is one example ,type is :{}", type);
        switch (type) {
            case "1":
                simpleThread.startOneThread();
                break;
            default:
                break;
        }
        return "ok";
    }
}
