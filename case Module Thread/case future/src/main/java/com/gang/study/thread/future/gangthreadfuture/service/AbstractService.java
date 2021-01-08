package com.gang.study.thread.future.gangthreadfuture.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class AbstractService implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void getTime(Long start, Long end) {
        logger.info("------> time is :{} <-------", Float.valueOf((end - start) / 1000));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> abstract is run <-------");
    }
}
