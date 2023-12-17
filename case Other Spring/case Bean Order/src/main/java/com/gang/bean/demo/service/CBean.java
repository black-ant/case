package com.gang.bean.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname BeanTwo
 * @Description TODO
 * @Date 2021/10/21
 * @Created by zengzg
 */
//@Service
public class CBean implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public CBean() {
        logger.info("------> 创建  CBean <-------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> CBean Run <-------");
    }
}
