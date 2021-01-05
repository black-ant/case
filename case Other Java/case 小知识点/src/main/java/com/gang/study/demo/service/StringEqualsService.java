package com.gang.study.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StringEqualsService
 * @Description TODO
 * @Date 2020/11/13 14:43
 * @Created by zengzg
 */
@Component
public class StringEqualsService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        test();
    }

    public void test() {

        for (int i = 0; i < 20; i++) {
            logger.info("------>++++++++++++++");
            String value = "0";
            String value2 = new String("0");
            logger.info("------> == : {} <-------", "0" == "0");
            logger.info("------> value == : {} <-------", value == "0");
            logger.info("------> new value == : {} <-------", value == value2);
            logger.info("------> new value == : {} <-------", "0" == value2);
            logger.info("------>equals :  {} <-------", "0".equals("0"));
        }

    }
}
