package com.gang.zookeeper.demo.controller;

import com.gang.zookeeper.demo.service.ZkRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2021/7/20
 * @Created by zengzg
 */
@RestController
@RequestMapping("/start")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ZkRequestService zkRequestService;

    @GetMapping("/test")
    public void test() {
        logger.info("------> test zookeeper start <-------");
    }
}
