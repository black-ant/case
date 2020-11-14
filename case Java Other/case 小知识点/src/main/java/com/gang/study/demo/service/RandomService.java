package com.gang.study.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * @Classname RandomService
 * @Description TODO
 * @Date 2020/11/13 15:17
 * @Created by zengzg
 */
@Component
public class RandomService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        test();
    }

    public void test() {
        for (int i = 0; i < 100; i++) {
            Long value = new SecureRandom().nextLong();
            logger.info("------> value :{} <-------", value);
        }
    }
}
