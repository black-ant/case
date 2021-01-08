package com.gang.study.thread.aqs.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartLogic
 * @Description TODO
 * @Date 2020/6/12 11:47
 * @Created by zengzg
 */
@Component
public class StartLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SimpleLock simpleLock = new SimpleLock();
        simpleLock.lock();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    simpleLock.lock();
                    System.out.println(Thread.currentThread().getId() + " acquired the lock!");
                    simpleLock.unlock();
                }
            }).start();
            // 简单的让线程按照for循环的顺序阻塞在lock上
            //目的是让线程顺序启动
            Thread.sleep(100);
        }

        System.out.println("main thread unlock!");
        simpleLock.unlock();

    }
}
