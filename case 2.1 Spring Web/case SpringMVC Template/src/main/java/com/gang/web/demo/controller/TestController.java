package com.gang.web.demo.controller;

import com.gang.web.demo.to.ModuleTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/1/7 17:47
 * @Created by zengzg
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private ApplicationContext applicationContext;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("get")
    public String get() {
        logger.info("------> get info <-------");
        applicationContext.getBean(SessionController.class);
        return "success";
    }


    @GetMapping("getParam/{key}")
    public String get(@PathVariable("key") String key, @RequestParam("name") String name) {
        logger.info("------> get info <-------");
        return "success";
    }


    @PostMapping("getBody")
    public ModuleTO getBody(@RequestBody ModuleTO moduleTO) {
        logger.info("------> module TO is :{} <-------", moduleTO);
        moduleTO.setName(moduleTO.getName() + "Inner");
        return moduleTO;
    }

    @GetMapping("cloud")
    public String cloud() {
        return "";
    }
}
