package com.test.thread.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

//@Service
public class JUCCyclicBarrierUtils implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(JUCCyclicBarrierUtils.class);



    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in <-------");


        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Runnable() {
            @Override
            public void run() {
                System.out.println("人到齐了，开会吧....");
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
                Thread.sleep(new Random().nextInt(1000));
                logger.info("------> this is sleep out <-------");
                barrier.await();
//                System.out.println(getName() + " 冲破栅栏 A");
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
