package com.gang.study.thread.consumerproduce.demo.logic6;


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

    public void set() {
        try {
            lock();
            while (!"".equals(ValueObject.value))
                condition.await();
            ValueObject.value = "123";
            System.out.println(Thread.currentThread().getName() + "生产了value, value的当前值是" + ValueObject.value);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            unlock();
        }
    }

    public void get() {
        try {
            lock();
            while ("".equals(ValueObject.value))
                condition.await();
            ValueObject.value = "";
            System.out.println(Thread.currentThread().getName() + "消费了value, value的当前值是" + ValueObject.value);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            unlock();
        }
    }
}
