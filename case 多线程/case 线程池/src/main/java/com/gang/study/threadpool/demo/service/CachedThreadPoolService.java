package com.gang.study.threadpool.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname CachedThreadPoolService
 * @Description TODO
 * @Date 2019/11/1 11:07
 * @Created by ant-black 1016930479@qq.com
 */
@Service
public class CachedThreadPoolService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() {
        cache();
    }

    public void cache() {
//        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//        ThreadFactory threadFactory = new ThreadFactory();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

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
    }
}
