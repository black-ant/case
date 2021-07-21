package com.gang.study.thread.consumerproduce.demo.logic2;


import com.sun.beans.decoder.ValueObject;

/**
 * @Classname Producer
 * @Description TODO
 * @Date 2020/4/19 16:40
 * @Created by zengzg
 */
public class Producer {
    private Object lock;

    public Producer(Object lock) {
        this.lock = lock;
    }

    public void setValue() {
        try {
            synchronized (lock) {
                if (!StorageObject.value.equals(""))
                    lock.wait();
                String value = System.currentTimeMillis() + "_" + System.nanoTime();
                System.out.println("Set的值是：" + value);
                StorageObject.value = value;

                Thread.sleep(1000);

                lock.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
