package com.gang.study.thread.consumerproduce.demo.logic4;


import com.gang.study.thread.consumerproduce.demo.logic3.StorageObject;
import com.sun.beans.decoder.ValueObject;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname Producer
 * @Description TODO
 * @Date 2020/4/19 16:40
 * @Created by zengzg
 */
public class ProducerAndConsumer extends ReentrantLock {

    private Condition condition = newCondition();

    public void set(Integer key) {
        try {
            lock();
            while (StorageObject.size() > 0)
                condition.await();

            synchronized (StorageObject.class) {
                StorageObject.add(key, "生产值" + key);
            }
            System.out.println(Thread.currentThread().getName() + " Set 生产的值是：" + key);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            unlock();
        }
    }

    public void get(Integer key) {
        try {
            lock();
            while (StorageObject.get(key) == null)
                condition.await();

            synchronized (StorageObject.class) {
                System.out.println(Thread.currentThread().getName() + " Get 消费的值是 :" + key + "：" + StorageObject.get(key));
            }
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            unlock();
        }
    }
}
