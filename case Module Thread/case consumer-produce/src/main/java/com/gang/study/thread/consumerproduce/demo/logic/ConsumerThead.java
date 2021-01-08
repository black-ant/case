package com.gang.study.thread.consumerproduce.demo.logic;

import org.springframework.stereotype.Component;

/**
 * @Classname ConsumerThead
 * @Description TODO
 * @Date 2020/4/19 13:34
 * @Created by zengzg
 */
public class ConsumerThead extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {

            try {
                System.out.println("----> 线程开始等待");
                Thread.currentThread().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            StorageEntity.get(i);
        }
    }
}
