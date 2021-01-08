package com.gang.study.thread.utils.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * 线程中抛出异常无法捕获
 */
@Service
public class ThreadExceptionCatch implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(ThreadExceptionCatch.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            new TaskThread().start();
        } catch (Exception e) {
            logger.info("------> catch e <-------");
        }

    }


    static class TaskThread extends Thread {

        @Override
        public void run() {
            throw new NullPointerException();
        }
    }
}
