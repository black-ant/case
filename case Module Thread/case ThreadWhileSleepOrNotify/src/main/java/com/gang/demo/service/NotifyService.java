package com.gang.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname NotifyService
 * @Description TODO
 * @Date 2022/2/19
 * @Created by zengzg
 */
@Service
public class NotifyService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        test();
    }

    public void test() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            Thread item = new Thread() {
                @Override
                public void run() {
                    try {
                        this.wait(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("------> 休眠5秒 <-------");
                }
            };
            item.start();
        }

        for (int i = 0; i < 10; i++) {
            notifyAll();
            Thread.sleep(5000);
        }
    }
}
