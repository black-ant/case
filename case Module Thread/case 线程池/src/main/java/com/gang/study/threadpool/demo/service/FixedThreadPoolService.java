package com.gang.study.threadpool.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class FixedThreadPoolService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() {
        logger.info("------> this is run in ThreadPoolService <-------");
        fixed();
    }

    public void fixed() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            logger.info("------> this is run 11111 <-------");
            Thread.sleep(1000);

            return null;
        });
        executor.submit(() -> {
            logger.info("------> this is run 22222 <-------");
            Thread.sleep(1000);

            return null;
        });
        executor.submit(() -> {
            logger.info("------> this is run 33333 <-------");
            Thread.sleep(1000);
            return null;
        });

        executor.submit(() -> {
            logger.info("------> this is run 44444 <-------");
            Thread.sleep(1000);
            return null;
        });

        executor.submit(() -> {
            logger.info("------> this is run 5555 <-------");
            Thread.sleep(1000);
            return null;
        });


        executor.submit(() -> {
            logger.info("------> this is run 66666 <-------");
            Thread.sleep(1000);
            return null;
        });

        executor.submit(() -> {
            logger.info("------> this is run 77777 <-------");
            Thread.sleep(1000);
            return null;
        });
        logger.info("------> poolsize :{} <-------", executor.getPoolSize());
        logger.info("------> queue size :{} <-------", executor.getQueue().size());

        executor.shutdown();

    }
}
