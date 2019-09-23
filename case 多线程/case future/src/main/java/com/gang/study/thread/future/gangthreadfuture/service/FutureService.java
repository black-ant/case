package com.gang.study.thread.future.gangthreadfuture.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

//@Service
public class FutureService extends AbstractService  implements ApplicationRunner, Callable<String> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Long startTime;
    private static Long endTime;

    private String para;

    public FutureService() {
    }

    public FutureService(String para) {
        this.para = para;
    }

    @Override
    public String call() throws Exception {

        logger.info("------> 业务逻辑开始执行 <-------");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        endTime = System.currentTimeMillis();
        logger.info("------> 业务执行完成 <-------");
        getTime(startTime, endTime);
        return sb.toString();
    }



    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is run <-------");
        FutureTask<String> future = new FutureTask<String>(new FutureService("a"));
        ExecutorService executor = Executors.newFixedThreadPool(1);
        startTime = System.currentTimeMillis();
        executor.submit(future);
        logger.info("------> 请求完毕！ <-------");
        try {
            logger.info("------> 主线程暂停 1 秒 <-------");
            Thread.sleep(2000);
            logger.info("------> 1 秒结束 ，主线程暂停 5 秒 <-------");
            Thread.sleep(5000);
            logger.info("------> 主线程暂停 5 秒 完成<-------");
        } catch (InterruptedException e) {

        }
        System.out.println("数据处理完成：" + future.get());
    }
}
