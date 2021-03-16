package com.test.thread.volatilethread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname VolatileService
 * @Description TODO
 * @Date 2021/3/13 14:58
 * @Created by zengzg
 */
@Component
public class VolatileService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> VolatileService <-------");
        atom();
    }


    /**
     * 测试 Volatile 的非原子性
     */
    public void atom() {
//        ThreadC[] threadCS = new ThreadC[100];
//
//        for (int i = 0; i < 100; i++) {
//            threadCS[i] = new ThreadC();
//        }
//
//        for (int i = 0; i < 100; i++) {
//            threadCS[i].start();
//        }


        ThreadD[] threadDSS = new ThreadD[100];

        for (int i = 0; i < 100; i++) {
            threadDSS[i] = new ThreadD();
        }

        for (int i = 0; i < 100; i++) {
            threadDSS[i].start();
        }
    }

}
