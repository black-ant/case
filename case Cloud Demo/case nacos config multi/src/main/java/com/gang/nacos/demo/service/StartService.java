package com.gang.nacos.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/10/11
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${user.name:default}")
    private String username;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in  :{}<-------", username);
    }
}
