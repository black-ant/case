package com.gang.aop.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/6/21
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> [测试 Application 启动时调用开始] <-------");
        get();
        logger.info("------> [测试 Application 启动时调用结束] <-------");
    }

    public String get() {
        logger.info("------> this is in StartService <-------");
        return "success";
    }


}
