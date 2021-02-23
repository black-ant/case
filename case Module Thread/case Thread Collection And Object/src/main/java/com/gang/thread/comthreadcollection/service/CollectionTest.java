package com.gang.thread.comthreadcollection.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname CollectionTest
 * @Description TODO
 * @Date 2020/11/25 10:39
 * @Created by zengzg
 */
@Component
public class CollectionTest implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    @Autowired
    private CollectionService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("------> 第一个线程 <-------");
        executor.submit(() -> {
            service.run();
        });

        logger.info("------> 第二个线程 <-------");
        executor.submit(() -> {
            service.run();
        });

        logger.info("------> 第三个线程 <-------");
        executor.submit(() -> {
            service.run();
        });

        logger.info("------> 第四个线程 <-------");
        executor.submit(() -> {
            service.run();
        });
    }

    public void test1() {
        service.run();
    }
}
