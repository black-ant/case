package com.gang.study.springsource.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class TestService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TestPropertySource testPropertySource;

    @Autowired
    TestEnviroment testEnviroment;

    @Autowired
    TestLocalMessage testLocalMessage;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        testPropertiesSource();
        testEnviroment.getApplicationProperties();
        testLocalMessage.getLocal();
    }

    public void testPropertiesSource() {
        logger.info("------------> :{}", testPropertySource.getTest());
        logger.info("------------> :{}", testPropertySource.getTest1());
        logger.info("------------> :{}", testPropertySource.getTest2());
    }
}
