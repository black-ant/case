package com.gang.study.resttemplate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2020/9/16 22:32
 * @Created by zengzg
 */
@Component
public class TestService implements ApplicationRunner {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> template is null :{} <-------", template == null);
    }
}
