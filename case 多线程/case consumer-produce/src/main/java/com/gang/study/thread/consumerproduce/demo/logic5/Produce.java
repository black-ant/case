package com.gang.study.thread.consumerproduce.demo.logic5;


import com.gang.study.thread.consumerproduce.demo.logic3.StorageObject;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname Producer
 * @Description TODO
 * @Date 2020/4/19 16:40
 * @Created by zengzg
 */
public class Produce extends PCFactory {

    public void get(Integer key) {
        try {
            lock();
            if (StorageObject.get(key) == null) {
                getConsumerCondition().signal();
                getProduceCondition().await();
            }
            System.out.println(Thread.currentThread().getName() + " logic 5 Get 消费的值是：" + StorageObject.get(key));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            unlock();
        }
    }
}
