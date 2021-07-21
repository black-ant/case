package com.gang.study.thread.future.gangthreadfuture.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * 超时控制
 */
//@Service
public class FutureTimeService extends AbstractService implements ApplicationRunner {

    private static Long startTime;
    private static Long endTime;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<String> future = executor.submit(new RunClass("ant-black"));
        logger.info("------> this is run  <-------");
        try {
            startTime = System.currentTimeMillis();
            future.get(1000 * 1, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            logger.error("----> 异常 超时");
        }
        logger.info("------> this is run over <-------");
    }

    class RunClass implements Callable<String> {

        private String para;

        public RunClass() {
        }

        public RunClass(String para) {
            this.para = para;
        }

        @Override
        public String call() throws Exception {

            logger.info("------> 业务逻辑开始执行 <-------");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 5; i++) {
                sb.append(para);
                try {
                    logger.info("------> this is run time :{} <-------", i);
                    Thread.sleep(300);
                } catch (InterruptedException e) {

                }
            }
            endTime = System.currentTimeMillis();
            logger.info("------> 业务执行完成 <-------");
            getTime(startTime, endTime);
            return sb.toString();
        }
    }
}
