package com.gang.study.thread.consumerproduce.demo.logic5;

import com.gang.study.thread.consumerproduce.demo.logic4.ProducerAndConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class Start5Logic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {


        logger.info("------> 55555555555 <-------");

        final Customer tdC = new Customer();
        Runnable producerRunnable = new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    tdC.set(i);
                }

            }
        };

        final Produce tdP = new Produce();
        Runnable customerRunnable = new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    tdP.get(i);
                }
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
