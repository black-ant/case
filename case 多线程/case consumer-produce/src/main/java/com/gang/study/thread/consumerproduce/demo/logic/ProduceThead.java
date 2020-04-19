package com.gang.study.thread.consumerproduce.demo.logic;

import org.springframework.stereotype.Component;

/**
 * @Classname ProduceThread
 * @Description TODO
 * @Date 2020/4/19 13:34
 * @Created by zengzg
 */
public class ProduceThead extends Thread {


    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            StorageEntity.add(i, " this is produce num :" + i);
            Thread.currentThread().notify();
        }
    }
}
