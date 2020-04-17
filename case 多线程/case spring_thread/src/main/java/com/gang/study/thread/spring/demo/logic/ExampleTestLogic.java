package com.gang.study.thread.spring.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname ExampleTestLogic
 * @Description TODO
 * @Date 2020/4/17 13:44
 * @Created by zengzg
 */
@Component
public class ExampleTestLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestLogic testLogic;


    /**
     * 1 正常情况下多线程调用 , 如果是存在于Spring 管理下的 , 可以不用担心为null的问题
     */
    public void run() {

        logger.info("Step 1 ");
        testLogic.test();

        logger.info("------>  <-------");

        // 消费数据
        Thread thread1 = new Thread(() -> {
            testLogic.test();
        });
        thread1.start();
    }
}
