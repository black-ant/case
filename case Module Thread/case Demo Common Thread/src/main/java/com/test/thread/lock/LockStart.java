package com.test.thread.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname LockStart
 * @Description TODO
 * @Date 2021/3/22
 * @Created by zengzg
 */
@Component
public class LockStart implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReentranLockTest reentranLockTest;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        reentranLockTest.test();
    }
}
