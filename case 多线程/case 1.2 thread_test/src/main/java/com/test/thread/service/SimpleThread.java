package com.test.thread.service;

import com.test.thread.mythread.ThreadOne;
import com.test.thread.mythread.ThreadStop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/22 23:14
 * @Version 1.0
 **/
@Service
public class SimpleThread {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void startOneThread() {
        ThreadOne thread1 = new ThreadOne();

        logger.info("start 线程是否存活：{}", thread1.isAlive());

        thread1.start();
        new Thread(thread1, "out1").start();
        new Thread(thread1, "out2").start();
        logger.info("end  线程是否存活：{}", thread1.isAlive());
    }

    public void stopThread() {
        ThreadStop thread1 = new ThreadStop();
        new Thread(thread1, "stop1").start();
    }
}
