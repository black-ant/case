package com.gang.study.many.two.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2020/1/7 14:01
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${gang.test}")
    private String test;

    @Value("${gang.test2}")
    private String test22;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is :{} <-------", test);
        logger.info("------> this is test22 :{} <-------", test22);
    }
}
