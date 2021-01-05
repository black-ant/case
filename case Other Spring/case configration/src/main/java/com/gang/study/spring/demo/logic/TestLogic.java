package com.gang.study.spring.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/4/24 10:35
 * @Created by zengzg
 */
@Component
public class TestLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestEntity testEntity;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in TestLogic:{} <-------", testEntity);
    }
}
