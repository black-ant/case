package com.gang.study.jerseyspringboot.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/4/15 23:26
 * @Created by zengzg
 */
@Service
public class TestLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void test() {
        logger.info("------> this is in testlogic <-------");
    }
}
