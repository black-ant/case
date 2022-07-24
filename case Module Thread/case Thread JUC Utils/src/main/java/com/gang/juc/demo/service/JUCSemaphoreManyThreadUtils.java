package com.gang.juc.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * @Classname JUCSemaphoreManyThreadUtils
 * @Description TODO
 * @Date 2022/3/16
 * @Created by zengzg
 */
@Service
public class JUCSemaphoreManyThreadUtils implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(JUCSemaphoreManyThreadUtils.class);

    private static ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(300);


    @Override
    public void run(ApplicationArguments args) throws Exception {
        doFlow();
    }

    public void doFlow() {

        final Semaphore notFull = new Semaphore(10);

        // 启动线程
        for (int i = 0; i <= 3; i++) {
            // 生产者
            Thread produce = new Thread(new Producer(notFull));
            produce.setName("Produce-" + i);
            produce.start();

            // 消费者
            Thread consumer = new Thread(new Consumer(notFull));
            consumer.setName("Consumer-" + i);
            consumer.start();
        }
    }

    // 生产者，负责增加
    static class Producer implements Runnable {

        Semaphore notFull;

        public Producer(Semaphore notFull) {
            this.notFull = notFull;
        }

        @Override
        public void run() {
            Integer num = 0;
            while (true) {
                try {
                    String name = "Num:" + (num++);
                    arrayBlockingQueue.put(name);
                    logger.info("------>  <-------");
                    System.out.println("Thread.currentThread().getName() " + name);
                    // 速度较快。休息10毫秒
                    Thread.sleep(5000);
                    notFull.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 消费者，负责减少
    static class Consumer implements Runnable {

        Semaphore notFull;

        public Consumer(Semaphore notFull) {
            this.notFull = notFull;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    notFull.acquire();
                    System.out.println("Thread.currentThread().getName()" + arrayBlockingQueue.take());
                    // 速度较慢，休息1000毫秒
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
