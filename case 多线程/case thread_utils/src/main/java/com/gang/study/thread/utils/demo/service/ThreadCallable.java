package com.gang.study.thread.utils.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

@Service
public class ThreadCallable implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThreadPoolTaskExecutor executor;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        FutureTask<String> backMsgResult = new FutureTask<String>(new TaskThread("one"));
        executor.submit(backMsgResult);
        FutureTask<String> backMsgResult1 = new FutureTask<String>(new TaskThread("two"));
        executor.submit(backMsgResult1);
        FutureTask<String> backMsgResult2 = new FutureTask<String>(new TaskThread("three"));
        executor.submit(backMsgResult2);

        logger.info("------> back :{} <-------", backMsgResult.get());
        logger.info("------> back :{} <-------", backMsgResult1.get());
        logger.info("------> back :{} <-------", backMsgResult2.get());
    }

    class TaskThread implements Callable<String> {

        private String msg;

        public TaskThread(String msg) {
            this.msg = msg;
        }

        @Override
        public String call() throws Exception {
            logger.info("------> this is cyclicBarrier  :{}<-------", msg);
            Thread.sleep(1000);
            logger.info("------> sleep is over   <-------");
            return "ok";
        }
    }
}
