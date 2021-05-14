package com.gang.web.demo.controller;

import com.gang.web.demo.to.ModuleTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/1/7 17:47
 * @Created by zengzg
 */
@RestController
@RequestMapping("test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("get")
    public String get() {
        logger.info("------> get info <-------");
        return "success";
    }


    @GetMapping("getParam/{key}")
    public String get(@PathVariable("key") String key, @RequestParam("name") String name) {
        logger.info("------> get info <-------");
        return "success";
    }


    @GetMapping("getBody")
    public String getBody(ModuleTO moduleTO) {
        logger.info("------> module TO is :{} <-------", moduleTO);
        return "success";
    }
}
