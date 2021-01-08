package com.gang.study.thread.lock.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartLogic
 * @Description TODO
 * @Date 2020/6/30 11:27
 * @Created by zengzg
 */
@Component
public class StartLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in StartLogic <-------");
    }
}
