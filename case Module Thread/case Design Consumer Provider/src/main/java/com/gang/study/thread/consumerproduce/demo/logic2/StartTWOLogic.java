package com.gang.study.thread.consumerproduce.demo.logic2;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartTWOLogic
 * @Description TODO
 * @Date 2020/4/19 16:41
 * @Created by zengzg
 */
//@Component
public class StartTWOLogic implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Object lock = new Object();
        final Producer producer = new Producer(lock);
        final Customer customer = new Customer(lock);

        Runnable producerRunnable = new Runnable() {
            public void run() {
                for (int i = 0; i < 20; i++) {
                    producer.setValue();
                }

            }
        };
        Runnable customerRunnable = new Runnable() {
            public void run() {
                for (int i = 0; i < 20; i++) {
                    customer.getValue();
                }
            }
        };

        Thread producerThread = new Thread(producerRunnable);
        Thread CustomerThread = new Thread(customerRunnable);

        producerThread.start();
        CustomerThread.start();
    }
}
