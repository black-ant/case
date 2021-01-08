package com.gang.study.thread.consumerproduce.demo.logic6;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname Start6Logic
 * @Description TODO
 * @Date 2020/4/19 18:29
 * @Created by zengzg
 */
//@Component
public class Start6Logic implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        final ProducerAndConsumer td = new ProducerAndConsumer();
        Runnable producerRunnable = new Runnable() {
            public void run() {
                for (int i = 0; i < Integer.MAX_VALUE; i++)
                    td.set();
            }
        };
        Runnable customerRunnable = new Runnable() {
            public void run() {
                for (int i = 0; i < Integer.MAX_VALUE; i++)
                    td.get();
            }
        };
        Thread ProducerThread = new Thread(producerRunnable);
        ProducerThread.setName("Producer");
        Thread ConsumerThread = new Thread(customerRunnable);
        ConsumerThread.setName("Consumer");
        ProducerThread.start();
        ConsumerThread.start();
    }
}
