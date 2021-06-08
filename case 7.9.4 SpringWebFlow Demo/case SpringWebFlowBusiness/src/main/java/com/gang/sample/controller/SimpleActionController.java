package com.gang.sample.controller;

import com.gang.sample.action.SimpleAction;
import com.gang.sample.service.SimpleActionCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SimpleActionController
 * @Description TODO
 * @Date 2021/6/7
 * @Created by zengzg
 */
@RestController
@RequestMapping("simpleAction")
public class SimpleActionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpleActionCreater creater;

    @GetMapping("createAction")
    public String createAction() {
        logger.info("------> [Rest createAction] <-------");

        creater.createSimpleActionFlowDefinition();

        return "success";
    }
}
