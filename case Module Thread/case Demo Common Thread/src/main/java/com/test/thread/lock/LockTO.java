package com.test.thread.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname LockTO
 * @Description TODO
 * @Date 2021/3/22
 * @Created by zengzg
 */
public class LockTO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Lock lock = new ReentrantLock();

    public void test() {
        lock.lock();
        for (int i = 0; i < 5; i++) {
            logger.info("------> CurrentThread [{}] , i : [{}] <-------", Thread.currentThread().getName(), i);
        }
        lock.unlock();
    }
}
