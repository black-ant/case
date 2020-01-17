package com.gang.study.threadpool.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname SingleThreadPoolService
 * @Description TODO
 * @Date 2019/11/1 10:59
 * @Created by ant-black 1016930479@qq.com
 */
@Service
public class SingleThreadPoolService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() {
        singleRun();
    }

    public void singleRun() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
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
        // 立即停止 ，后续均不执行
//        executor.shutdownNow();
//        executor.shutdown();
        logger.info("------> poolsize :{} <-------", executor.isShutdown());

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

    }


}
