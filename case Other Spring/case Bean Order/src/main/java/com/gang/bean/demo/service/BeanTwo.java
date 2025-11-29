package com.gang.bean.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @Classname BeanTwo
 * @Description TODO
 * @Date 2021/10/21
 * @Created by zengzg
 */
@Service
@Order(300)
public class BeanTwo implements ApplicationRunner,IBaseBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public BeanTwo() {
        logger.info("------> 创建 BeanTwo <-------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> BeanTwo Run <-------");
    }

}
