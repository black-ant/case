package com.java.classlayout.demo.service;

import org.openjdk.jol.info.ClassLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2021/8/11
 * @Created by zengzg
 */
@Component
public class TestService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> Class 类型信息 : [{}] <-------", ClassLayout.parseInstance(this).toPrintable());
    }
}
