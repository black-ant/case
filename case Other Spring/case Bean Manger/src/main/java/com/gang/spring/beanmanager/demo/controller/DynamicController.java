package com.gang.spring.beanmanager.demo.controller;

import com.gang.spring.beanmanager.demo.dynamic.DynamicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname DynamicController
 * @Description TODO
 * @Date 2021/2/18 10:08
 * @Created by zengzg
 */
@RestController
@RequestMapping("/dynamic")
public class DynamicController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DynamicService dynamicService;

    @GetMapping("/test")
    public String testDynamic() {

        logger.info("------> 修改Bean前 <-------");
        dynamicService.show();

        logger.info("------> 修改Bean后 <-------");
        dynamicService.show();

        return "success";
    }
}
