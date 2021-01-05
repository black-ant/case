package com.gang.study.cloud.nacos.gangcloudnacos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname ConfigService
 * @Description TODO
 * @Date 2020/1/17 9:56
 * @Created by zengzg
 */
@Component
public class ConfigService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${gang.test.name:'default_test'}")
    private String param1;

    public void testConfig() {
        logger.info("------> this is param1: {} <-------", param1);
    }

    public void run(ApplicationArguments args) throws Exception {
        testConfig();
    }
}
