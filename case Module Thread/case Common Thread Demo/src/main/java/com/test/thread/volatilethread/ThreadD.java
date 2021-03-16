package com.test.thread.volatilethread;

import com.test.thread.to.CommonTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname ThreadB
 * @Description TODO
 * @Date 2021/2/28 19:52
 * @Created by zengzg
 */
public class ThreadD extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ThreadC.class);

    volatile public static int count;

    synchronized public static void addCount() {
        for (int i = 0; i < 100; i++) {
            count++;
        }
        logger.info("------> ThreadD count :{} <-------", count);
    }

    @Override
    public void run() {
        addCount();
    }

}
