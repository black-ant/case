package com.gang.study.thread.consumerproduce.demo.logic3;


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

    public void setValue(Integer key) {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + "Set的值是：" + key);
            StorageObject.add(key, "生产消息 : " + key);

            lock.notify();

            try {
                Thread.currentThread().sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
