package com.gang.study.source.springboot.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @Classname TestXmlBean
 * @Description TODO
 * @Date 2021/2/22 15:47
 * @Created by zengzg
 */
public class TestXmlBean implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String showInfo() {
        logger.info("------> this bean is load <-------");
        return "success";
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        showInfo();
    }
}
