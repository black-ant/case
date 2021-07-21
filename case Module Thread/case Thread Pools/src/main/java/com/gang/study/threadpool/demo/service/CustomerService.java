package com.gang.study.threadpool.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Classname CustomerService
 * @Description TODO
 * @Date 2021/3/14 11:17
 * @Created by zengzg
 */
@Component
public class CustomerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() throws Exception {
        logger.info("------> CustomerService <-------");

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 6, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3));


        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                logger.info("------> this is run 22222 <-------");
                Thread.sleep(50000);
                return null;
            });
        }
    }
}
