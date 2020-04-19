package com.gang.study.thread.consumerproduce.demo.logic2;

import com.sun.beans.decoder.ValueObject;

/**
 * @Classname Customer
 * @Description TODO
 * @Date 2020/4/19 16:41
 * @Created by zengzg
 */
public class Customer {
    private Object lock;

    public Customer(Object lock) {
        this.lock = lock;
    }

    public void getValue() {
        try {
            synchronized (lock) {
                if (StorageObject.value.equals(""))
                    lock.wait();
                System.out.println("Get的值是：" + StorageObject.value);
                StorageObject.value = "";
                lock.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
