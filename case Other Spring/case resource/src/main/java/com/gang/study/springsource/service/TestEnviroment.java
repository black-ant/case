package com.gang.study.springsource.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TestEnviroment {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Value("${com.gang1.study.test5}")
    private String test5;

    public void getApplicationProperties() {
        logger.info("------>4 :{}", env.getProperty("com.gang1.study.test4"));
        logger.info("------>5 :{}", test5);
    }

}
