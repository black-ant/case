package com.gang.study.source.springboot.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Classname CommonService
 * @Description TODO
 * @Date 2021/3/2 15:24
 * @Created by zengzg
 */
public class Common2Service {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void initMethod() {
        logger.info("------> this is in initMethod <-------");
    }

}
