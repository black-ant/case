package com.gang.study.thread.spring.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname TestClass
 * @Description TODO
 * @Date 2020/4/17 13:44
 * @Created by zengzg
 */
@Component
public class TestLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void test() {
        logger.info("------> this is in test <-------");
    }
}
