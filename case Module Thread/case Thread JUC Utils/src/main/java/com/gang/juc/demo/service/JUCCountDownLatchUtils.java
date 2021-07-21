package com.gang.juc.demo.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

/**
 * CountDownLatch 的 使用
 */
//@Service
public class JUCCountDownLatchUtils implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(JUCCountDownLatchUtils.class);

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        test();
    }

    public void test() throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(5);

        //Boss线程启动
        new BossThread(countDownLatch).start();

        for (Long i = Long.valueOf("0"), j = countDownLatch.getCount(); i < j; i++) {
            FutureTask<String> backMsgResult = new FutureTask<String>(
                    new TaskThread(countDownLatch));

            executor.submit(backMsgResult);

            logger.info("------> back is :{} <-------", backMsgResult.get());
        }
    }

    class TaskThread implements Callable<String> {

        private CountDownLatch countDown;

        public TaskThread(CountDownLatch countDown) {
            this.countDown = countDown;
        }

        @Override
        public String call() throws Exception {
            logger.info("------> this is cyclicBarrier  <-------");
            Thread.sleep(1000);
            logger.info("------> sleep is over   <-------");
            countDown.countDown();
            return "ok";
        }
    }

    static class BossThread extends Thread {

        private CountDownLatch countDown;

        public BossThread(CountDownLatch countDown) {
            this.countDown = countDown;
        }

        @Override
        public void run() {
            System.out.println("Boss在会议室等待，总共有" + countDown.getCount() + "个人开会...");
            try {
                //Boss等待
                countDown.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("所有人都已经到齐了，开会吧...");
        }
    }
}
