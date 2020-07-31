package com.gang.study.resttemplate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2019/12/17 15:17
 * @Created by zengzg
 */
@Service
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in run <-------");
        test();
    }


    public void test() {
        String back = restTemplate.getForObject("http://localhost:8089/user/test", String.class);
        logger.info("------> back is :{} <-------", back);
    }

}
