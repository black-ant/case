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

@Service
public class FutureService extends AbstractService implements ApplicationRunner, Callable<String> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Long startTime;
    private static Long endTime;

    private String salt;
    private Integer sleepNum;

    public FutureService() {
    }

    public FutureService(String salt, Integer sleepNum) {
        this.salt = salt;
        this.sleepNum = sleepNum;
    }

    @Override
    public String call() throws Exception {

        logger.info("------> 业务逻辑开始执行 :{} <-------", salt);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sleepNum; i++) {
            sb.append(salt);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
        endTime = System.currentTimeMillis();
        logger.info("------> {} - 业务执行完成 :{} <-------", salt, sb.toString());
        getTime(startTime, endTime);
        return sb.toString();
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is run <-------");
        ExecutorService executor = Executors.newFixedThreadPool(3);

        logger.info("------> 开始业务一 future - a :  <-------");
        FutureTask<String> future = new FutureTask<String>(new FutureService("a", 10));
        startTime = System.currentTimeMillis();
        executor.submit(future);
        logger.info("------> 业务一请求完毕！主线程执行 <-------");

        logger.info("------> 开始业务二 future - b :  <-------");
        FutureTask<String> future2 = new FutureTask<String>(new FutureService("b", 5));
        startTime = System.currentTimeMillis();
        executor.submit(future2);
        logger.info("------> 业务三请求完毕！主线程执行 <-------");

        logger.info("------> 开始业务三 future - c :  <-------");
        FutureTask<String> future3 = new FutureTask<String>(new FutureService("c", 3));
        startTime = System.currentTimeMillis();
        executor.submit(future3);
        logger.info("------> 业务三请求完毕！主线程执行 <-------");

//        try {
//            logger.info("------> 主线程暂停 1 秒 <-------");
//            Thread.sleep(2000);
//            logger.info("------> 1 秒结束 ，主线程暂停 5 秒 <-------");
//            Thread.sleep(5000);
//            logger.info("------> 主线程暂停 5 秒 完成<-------");
//        } catch (InterruptedException e) {
//            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
//        }
        logger.info("------> future2 数据处理完成：{} <-------", future2.get());
        logger.info("------> 2-1 测试主线程是否阻塞 <-------");
        logger.info("------> future1 数据处理完成：{} <-------", future.get());
        logger.info("------> 1-3 测试主线程是否阻塞 <-------");
        logger.info("------> future3 数据处理完成：{} <-------", future3.get());
    }
}
