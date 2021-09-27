package com.gang.cloud.template.demo.controller;

import com.gang.cloud.template.demo.config.DefaultHystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname CommandClassController
 * @Description 通过继承 HystrixCommand 类实现
 * @Date 2021/9/26
 * @Created by zengzg
 */

@RestController
@RequestMapping("class")
public class CommandClassController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    DefaultHystrixCommand defaultHystrixCommand;

    @RequestMapping(value = "/commandhello", method = RequestMethod.GET)
    public String commandhello() throws Exception {
        logger.info("------> this is in commandhello <-------");
        defaultHystrixCommand.execute();
        logger.info("------> 执行后续逻辑 <-------");
        return "Controller";
    }
}
