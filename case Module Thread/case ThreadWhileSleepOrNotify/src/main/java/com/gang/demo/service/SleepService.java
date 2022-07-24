package com.gang.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname SleepService
 * @Description TODO
 * @Date 2022/2/19
 * @Created by zengzg
 */
@Service
public class SleepService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        test();
    }

    public void test() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            Thread item = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        try {
                            Thread.sleep(15000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            item.start();
        }
        Long end = System.currentTimeMillis();
        logger.info("------> 全部执行完成 :{} <-------", end - start);
    }
}
