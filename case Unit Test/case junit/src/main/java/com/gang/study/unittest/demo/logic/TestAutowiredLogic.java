package com.gang.study.unittest.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname TestAutowiredLogic
 * @Description TODO
 * @Date 2020/7/6 17:57
 * @Created by zengzg
 */
@Component
public class TestAutowiredLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestLogic testLogic;

    public void test() {
        logger.info("------> this is in test <-------");
        testLogic.doTest();

        logger.info("------> this is return :{} <-------", testLogic.returnTest("1"));
    }

}
