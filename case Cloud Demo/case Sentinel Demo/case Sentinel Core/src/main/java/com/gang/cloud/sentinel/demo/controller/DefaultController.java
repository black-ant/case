package com.gang.cloud.sentinel.demo.controller;

import com.gang.cloud.sentinel.demo.logic.DefaultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2021/6/22
 * @Created by zengzg
 */
@RestController
@RequestMapping("default")
public class DefaultController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DefaultService startService;

    @GetMapping("get")
    public String get() {
        return startService.get();
    }


    /**
     * 此处 QPS 访问 > 20 时 , 会抛出异常
     *
     * @param qps
     * @return
     */
    @GetMapping("flowControl")
    public String flowControl(@RequestParam(name = "qps", defaultValue = "1") Integer qps) {
        logger.info("------> [Sentinel 流量控制 Demo] <-------");
        return startService.flowControl(qps);
    }

    /**
     * 通过注解限流
     *
     * @param qps
     * @return
     */
    @GetMapping("flowControlByAnnotation")
    public String flowControlByAnnotation(@RequestParam(name = "qps", defaultValue = "1") Integer qps) {
        logger.info("------> [Sentinel 流量控制 Demo] <-------");
        return startService.flowControlByAnnotation(qps);
    }


    /**
     * 通过注解限流
     *
     * @param qps
     * @return
     */
    @GetMapping("flowControlSync")
    public String flowControlSync(@RequestParam(name = "qps", defaultValue = "1") Integer qps) {
        logger.info("------> [Sentinel 流量控制 Demo] <-------");
        return startService.flowControlByAnnotation(qps);
    }
}
