package com.test.thread.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Classname ReentrantReadWriteLock
 * @Description TODO
 * @Date 2021/3/25
 * @Created by zengzg
 */
public class ReentrantReadWriteLockTest {


    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    Object data;
    volatile boolean cacheValid;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    void processCachedData() {
        rwl.readLock().lock();
        if (!cacheValid) {
            rwl.readLock().unlock();
            rwl.writeLock().lock();
            try {
                if (!cacheValid) {
                    data = "test";
                    cacheValid = true;
                }
                rwl.readLock().lock();
            } finally {
                rwl.writeLock().unlock(); // Unlock write, still hold read
            }
        }
    }
}
