package com.gang.study.threadpool.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @Classname SingleThreadScheduledExecutorService
 * @Description TODO
 * @Date 2019/11/1 11:11
 * @Created by ant-black 1016930479@qq.com
 */
@Service
public class SingleThreadScheduledExecutorService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public void run() {
        //        schedule();
        //        example2();
    }

    public void schedule() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // scheduleWithFixedDelay 是反复运行 ,执行间隔从执行开始时间算
        // scheduleAtFixedRate 反复执行 ，执行时间是上传执行完成

        executor.scheduleWithFixedDelay(() -> {
            logger.info("------> this is run 11111 <-------");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);


        executor.scheduleAtFixedRate(() -> {
            logger.info("------> this is run 2222 <-------");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);

    }

    public void example2() {
        CountDownLatch lock = new CountDownLatch(3);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            System.out.println("Hello World");
            lock.countDown();
        }, 500, 100, TimeUnit.MILLISECONDS);

        try {
            lock.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        future.cancel(true);
    }



}


