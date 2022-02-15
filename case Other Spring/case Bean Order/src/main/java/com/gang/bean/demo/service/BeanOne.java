package com.gang.bean.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname BeanOne
 * @Description TODO
 * @Date 2021/10/21
 * @Created by zengzg
 */
@Service
public class BeanOne implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BeanTwo beanTwo;

    public BeanOne() {
        logger.info("------> 创建 BeanOne <-------");
    }

    public void run() {
        logger.info("------> this is in bean TWO <-------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> BeanOne Run <-------");
    }
}
