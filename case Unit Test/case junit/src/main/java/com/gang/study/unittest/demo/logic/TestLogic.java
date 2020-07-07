package com.gang.study.unittest.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/7/6 15:17
 * @Created by zengzg
 */
@Component
public class TestLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String doTest() {
        logger.info("------> this is in test <-------");
        return "success";
    }

    public String returnTest(String name) {
        logger.info("------> this is in returnTest <-------");
        return "oneTest";
    }

}
