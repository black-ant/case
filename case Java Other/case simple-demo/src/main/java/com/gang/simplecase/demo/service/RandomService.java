package com.gang.simplecase.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * @Classname RandomService
 * @Description TODO
 * @Date 2020/9/15 10:45
 * @Created by zengzg
 */
@Component
public class RandomService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        test();
    }

    public void test() {
        logger.info("------> this is in Random Service <-------");

        for (int i = 0; i < 10; i++) {
            logger.info("------> random :{} <-------", new SecureRandom().nextLong());
            logger.info("------> abs :{} <-------", Math.abs(new SecureRandom().nextLong()));

        }
    }
}
