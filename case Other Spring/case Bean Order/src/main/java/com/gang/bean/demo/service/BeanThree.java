package com.gang.bean.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Classname BeanOThree
 * @Description TODO
 * @Date 2021/10/21
 * @Created by zengzg
 */
@Service
@Order(70)
public class BeanThree implements ApplicationRunner,IBaseBean{

//    @Autowired
//    private CBean cBean;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public BeanThree(BeanTwo beanTwo) {
        logger.info("------> 创建 BeanThree <-------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> BeanThree Run <-------");
    }

}
