package com.gang.spring.exceptionhandle.demo.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestSevice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void setviceTest() {
        logger.info("this is in service");
        throw new TestException();
    }
}
