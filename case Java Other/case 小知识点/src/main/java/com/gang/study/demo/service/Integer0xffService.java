package com.gang.study.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname Integer0xffService
 * @Description Integer 补码案例
 * @Date 2020/11/13 14:55
 * @Created by zengzg
 */
@Component
public class Integer0xffService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        test();
    }

//    public void test() {
//        int result = 0;
//        for (int i = 0; i < 4; i++) {
//            result = (result << 8) | readByte();
//        }
//        logger.info("------> :{} <-------", result);
//    }
}
