package com.gang.aop.demo.controller;

import com.gang.aop.demo.service.StartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2021/6/22
 * @Created by zengzg
 */
@RequestMapping("/start")
@RestController
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private StartService startService;

    @GetMapping("get")
    public String get() {
        logger.info("------> [测试启动后调用开始] <-------");
        String back = startService.get();
        logger.info("------> [测试启动后调用结束] <-------");
        return back;
    }
}
