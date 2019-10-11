package com.gang.study.thread.utils.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class ThreadParamShard implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(ThreadParamShard.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is run <-------");
    }
}
