package com.gang.study.thread.consumerproduce.demo.logic3;

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

    public void getValue(Integer key) {
        try {
            synchronized (lock) {
                if (StorageObject.get(key) == null) {
                    lock.wait();
                }
                System.out.println(Thread.currentThread().getName() + "Get的值是：" + StorageObject.get(key));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
