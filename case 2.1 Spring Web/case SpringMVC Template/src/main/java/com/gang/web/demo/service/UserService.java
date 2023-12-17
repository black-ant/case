package com.gang.web.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2022/9/25
 * @Created by zengzg
 */
@Component
public class UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String test() {
        logger.info("------> 调用到UserService <-------");
        return "success";
    }
}
