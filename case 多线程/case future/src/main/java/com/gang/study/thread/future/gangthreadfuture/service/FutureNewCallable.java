package com.gang.study.thread.future.gangthreadfuture.service;

import lombok.Data;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.*;

@Service
public class FutureNewCallable extends AbstractService {

    private static Long startTime;
    private static Long endTime;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<String> future = executor.submit(createCallable());
        logger.info("------> this is run  <-------");
        try {
            startTime = System.currentTimeMillis();
            future.get(1000 * 2, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            logger.error("----> 异常 超时");
        }
        logger.info("------> this is run over <-------");
    }

    public Callable createCallable() {
        Callable<Module> call = new Callable<Module>() {
            public Module call() throws Exception {

                Module para = new Module();
                logger.info("------> 业务逻辑开始执行 <-------");
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < 10; i++) {
                    sb.append("time").append(i);
                    para.setName(para.getName() + sb.toString());
                    logger.info("------> time is :{} <-------", sb.toString());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }
                endTime = System.currentTimeMillis();
                logger.info("------> 业务执行完成 <-------");
                getTime(startTime, endTime);
                return para;
            }
        };
        return call;
    }

    @Data
    class Module {
        private String name;
        private Date date;
    }
}
