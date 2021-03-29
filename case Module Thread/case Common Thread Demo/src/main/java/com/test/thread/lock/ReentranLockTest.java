package com.test.thread.lock;

import com.test.thread.volatilethread.ThreadD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname ReentranLockTest
 * @Description TODO
 * @Date 2021/3/22
 * @Created by zengzg
 */
@Component
public class ReentranLockTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void test() {
        logger.info("------> [ReentranLock Task Start] <-------");

        LockTO lockTO = new LockTO();


        LockCommonThread[] threadDSS = new LockCommonThread[10];

        for (int i = 0; i < 10; i++) {
            threadDSS[i] = new LockCommonThread(lockTO);
        }

        for (int i = 0; i < 10; i++) {
            threadDSS[i].start();
        }

    }


}
