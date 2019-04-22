package com.test.thread.service;

import com.test.thread.mythread.ThreadOne;
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
        Long time = System.currentTimeMillis();
        logger.info("线程开始运行,开始时间：{}", time);
        thread1.start();
        new Thread(thread1,"out1").start();
        new Thread(thread1,"out2").start();
        logger.info("线程运行结束,运行时间：{}", System.currentTimeMillis() - time);
    }
}
