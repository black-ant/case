package com.gang.spring.beanmanager.demo.controller;

import com.gang.spring.beanmanager.demo.interfaceTest.IUserOperation;
import com.gang.spring.beanmanager.demo.interfaceTest.UserOperationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname InterfaceController
 * @Description TODO
 * @Date 2020/12/21 11:02
 * @Created by zengzg
 */
@RestController
@RequestMapping("/interface")
public class InterfaceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IUserOperation userOperation;
    @Autowired
    UserOperationImpl userOperationImpl;

    @GetMapping
    public String test() {

        logger.info("------> this is in test <-------");
        userOperation.doOperation();
        userOperationImpl.doOperation();
        return "success";
    }
}
