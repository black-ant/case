package com.test.thread.service;

/**
 * @Classname SynchronizedService
 * @Description TODO
 * @Date 2021/8/14
 * @Created by zengzg
 */
public class SynchronizedService {
    public void method() {
        synchronized (this) {
            System.out.println("synchronized 代码块");
        }
    }
}
