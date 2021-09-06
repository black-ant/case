package com.gang.zookeeper.demo.controller;

import com.gang.zookeeper.demo.service.RegistryTemplate;
import com.gang.zookeeper.demo.service.ZkRequestService;
import com.gang.zookeeper.demo.watch.ZkWatchService;
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

    @Autowired
    private ZkWatchService zkWatchService;

    @Autowired
    private RegistryTemplate registryTemplate;

    @GetMapping("/test")
    public void test() {
        logger.info("------> test zookeeper start <-------");
        try {
            registryTemplate.run();
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }
    }

    @GetMapping("/watch")
    public void watch() {
        logger.info("------> test zookeeper start <-------");
        zkWatchService.run();
    }
}
