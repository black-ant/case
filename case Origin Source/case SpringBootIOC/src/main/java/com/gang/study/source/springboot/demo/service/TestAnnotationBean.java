package com.gang.study.source.springboot.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname TestAnnotationBean
 * @Description TODO
 * @Date 2021/2/22 16:45
 * @Created by zengzg
 */
@Service
public class TestAnnotationBean implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String showInfo() {
        logger.info("------> this bean is load TestAnnotationBean <-------");
        return "success";
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        showInfo();
    }

}
