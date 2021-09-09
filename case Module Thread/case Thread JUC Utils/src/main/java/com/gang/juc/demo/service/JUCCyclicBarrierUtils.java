package com.gang.juc.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

@Service
public class JUCCyclicBarrierUtils implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(JUCCyclicBarrierUtils.class);


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        test();
    }

    public void test() throws Exception {
        logger.info("------> this is in <-------");


        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Runnable() {
            @Override
            public void run() {
                System.out.println("人到齐了，开会吧....");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        for (int i = 0; i < 9; i++) {
            new TaskThread(cyclicBarrier).start();
        }

        cyclicBarrier.await();
        logger.info("------> countinue <-------");
    }

    static class TaskThread extends Thread {

        CyclicBarrier barrier;

        public TaskThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(getName() + " 到达栅栏 A");
                Thread.sleep(new Random().nextInt(3000));
                logger.info("------> this is sleep out <-------");
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 A");
//
//                Thread.sleep(2000);
//                System.out.println(getName() + " 到达栅栏 B");
//                barrier.await();
//                System.out.println(getName() + " 冲破栅栏 B");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
