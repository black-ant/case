package com.gang.bean.demo.service2;

import com.gang.bean.demo.service.BeanTwo;
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
//@Service
public class BeanOne1 implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public BeanOne1() {
        logger.info("------> 创建 BeanOne1 <-------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> BeanOne1 Run <-------");
    }
}
