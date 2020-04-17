package com.gang.study.thread.commonly.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname ThreadCommon
 * @Description TODO
 * @Date 2020/4/17 12:15
 * @Created by zengzg
 */
@Component
public class ThreadCommon implements ApplicationRunner {

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
