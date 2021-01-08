package com.gang.study.thread.aqs.demo.logic;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Classname SimpleLock
 * @Description TODO
 * @Date 2020/6/12 13:40
 * @Created by zengzg
 */
public class SimpleLock extends AbstractQueuedSynchronizer {

    @Override
    protected boolean tryAcquire(int unused) {
        //使用compareAndSetState控制AQS中的同步变量
        if (compareAndSetState(0, 1)) {
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    @Override
    protected boolean tryRelease(int unused) {
        setExclusiveOwnerThread(null);
        //使用setState控制AQS中的同步变量
        setState(0);
        return true;
    }

    public void lock() {
        acquire(1);
    }

    public boolean tryLock() {
        return tryAcquire(1);
    }

    public void unlock() {
        release(1);
    }

}
