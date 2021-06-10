package com.gang.cloud.resilience4j.controller;

import com.gang.cloud.resilience4j.service.SimpleCircuitBreakerDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2021/6/10
 * @Created by zengzg
 */
@RestController
@RequestMapping("start")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpleCircuitBreakerDemoService demoService;

    @GetMapping("/")
    public String testSrart() {
        logger.info("------> Start Controller 运行成功 <-------");
        return "success";
    }

    @GetMapping("/SimpleCircuitBreaker")
    public String doSimpleCircuitBreaker() {
        demoService.run();
        return "success";
    }


}
