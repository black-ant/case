package com.test.thread.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @Classname SampleThread
 * @Description TODO
 * @Date 2021/2/28 18:58
 * @Created by zengzg
 */
public class SampleThread implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("------> ThreadCommon this is id :{} <-------", Thread.currentThread().getId());
        otherDate();

        // 消费数据
        Thread thread1 = new Thread(() -> consumeData());
        thread1.start();

        Thread.sleep(2000);

        // 消费数据
        Thread thread = new Thread(() -> consumeData());
        thread.start();
        thread.yield();
        thread.interrupt();

        // 消费数据
        Thread thread2 = new Thread(() -> consumeData());
        thread2.start();

    }

    public void consumeData() {
        logger.info("------> consumeData this is id :{} <-------", Thread.currentThread().getId());
    }

    public void otherDate() {
        logger.info("------> otherDate this is id :{} <-------", Thread.currentThread().getId());
    }

}

