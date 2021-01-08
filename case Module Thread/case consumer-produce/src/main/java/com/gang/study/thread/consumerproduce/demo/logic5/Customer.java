package com.gang.study.thread.consumerproduce.demo.logic5;

import com.gang.study.thread.consumerproduce.demo.logic3.StorageObject;


/**
 * @Classname Customer
 * @Description TODO
 * @Date 2020/4/19 16:41
 * @Created by zengzg
 */
public class Customer extends PCFactory {

    public void set(Integer key) {
        try {
            lock();
            if (key % 10 == 0) {
                getConsumerCondition().await();
            }
            System.out.println(Thread.currentThread().getName() + " logic 5 Set的值是：" + key);
            StorageObject.add(key, "生产消息 : " + key);
            getProduceCondition().signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            unlock();
        }
    }
}
