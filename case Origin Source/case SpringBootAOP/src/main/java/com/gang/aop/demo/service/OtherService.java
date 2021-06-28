package com.gang.aop.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname OtherService
 * @Description TODO
 * @Date 2021/6/24
 * @Created by zengzg
 */
@Component
public class OtherService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String get() {
        logger.info("------> this is in StartService <-------");
        return "success";
    }
}
