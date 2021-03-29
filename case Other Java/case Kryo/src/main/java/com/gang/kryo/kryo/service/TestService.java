package com.gang.kryo.kryo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2021/3/24
 * @Created by zengzg
 */
@Component
public class TestService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestInnerService testInnerService;

    public String test() {
        logger.info("------> do TestService <-------");
        return testInnerService.doTest();
    }
}
