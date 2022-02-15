package com.gang.bean.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname BeanOThree
 * @Description TODO
 * @Date 2021/10/21
 * @Created by zengzg
 */
@Service
public class BeanOThree implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BeanOne beanOne;

    public BeanOThree() {
        logger.info("------> 创建 BeanOThree <-------");
    }

    public void run() {
        logger.info("------> this is in bean TWO <-------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> BeanOThree Run <-------");
    }

}
