package com.gang.cloud.sentinel.demo.controller;

import com.gang.cloud.sentinel.demo.logic.DefaultService;
import com.gang.cloud.sentinel.demo.logic.RemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname RemoteController
 * @Description
 * @Date 2021/8/3
 * @Created by zengzg
 */
@RestController
@RequestMapping("remote")
public class RemoteController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RemoteService remoteService;

    @GetMapping("get")
    public String get() {
        return remoteService.get();
    }

    @GetMapping("flowControl")
    public String flowControl(@RequestParam(name = "qps", defaultValue = "1") Integer qps) {
        logger.info("------> [Sentinel 流量控制 Demo] <-------");
        return remoteService.flowControl(qps);
    }

}
