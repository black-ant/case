package com.test.thread.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

/**
 * @Classname JUCSemaphoreUtils
 * @Description TODO
 * @Date 2021/3/16
 * @Created by zengzg
 */
@Component
public class JUCSemaphoreUtils implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Parking parking = new Parking(3);

        for (int i = 0; i < 5; i++) {
            new Car(parking).start();
        }
    }

    static class Parking {

        //信号量
        private Semaphore semaphore;

        Parking(int count) {
            semaphore = new Semaphore(count);
        }

        public void park() {
            try {
                //获取信号量
                semaphore.acquire();
                long time = (long) (Math.random() * 10);
                System.out.println(Thread.currentThread().getName() + "进入停车场，停车" + time + "秒...");
                Thread.sleep(time);
                System.out.println(Thread.currentThread().getName() + "开出停车场...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }


    static class Car extends Thread {
        Parking parking;

        Car(Parking parking) {
            this.parking = parking;
        }

        @Override
        public void run() {
            parking.park();     //进入停车场
        }
    }

}
