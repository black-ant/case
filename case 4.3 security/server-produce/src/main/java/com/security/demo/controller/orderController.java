package com.security.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/14 15:50
 * @Version 1.0
 **/
@RestController
public class orderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("save")
    public String saveOrder() {
        logger.info("save order is ok:{}", "success");
        return "ok";
    }
}
