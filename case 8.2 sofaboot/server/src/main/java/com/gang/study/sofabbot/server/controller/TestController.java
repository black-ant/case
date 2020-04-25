package com.gang.study.sofabbot.server.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.gang.study.focase.demo.service.FacadeService;
import com.gang.study.provide.ProvideService;
import com.gang.study.sofaboot.api.config.APIService;
import com.gang.study.sofaboot.bean.config.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/4/25 21:24
 * @Created by zengzg
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @SofaReference
    private FacadeService provideService;

    @SofaReference(uniqueId = "testServiceImpl")
    private TestService testService;

    @SofaReference(uniqueId = "apiServiceImpl")
    private APIService apiService;

    @GetMapping("/hello")
    public String hello() throws IOException {
        logger.info("------> this is in TestController <-------");
        return provideService.message();
    }

    @GetMapping("/bean")
    public String bean() {
        logger.info("------> this is in bean <-------");
        return testService.send();
    }

    @GetMapping("/api")
    public String api() {
        logger.info("------> this is in api <-------");
        return apiService.send();
    }
}
