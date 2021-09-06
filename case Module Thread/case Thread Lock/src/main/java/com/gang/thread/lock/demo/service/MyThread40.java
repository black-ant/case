package com.gang.thread.lock.demo.service;

/**
 * @Classname MyThread40
 * @Description TODO
 * @Date 2021/8/19
 * @Created by zengzg
 */
public class MyThread40 extends Thread {
    private ThreadDomain40 td;

    public MyThread40(ThreadDomain40 td) {
        this.td = td;
    }

    public void run() {
        td.await();
    }
}
