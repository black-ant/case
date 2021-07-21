package com.test.thread.common;

import com.test.thread.to.CommonTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname ThreadA
 * @Description TODO
 * @Date 2021/2/28 19:52
 * @Created by zengzg
 */
public class ThreadA extends Thread {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CommonTO commonTO;

    public ThreadA(CommonTO commonTO) {
        super();
        this.commonTO = commonTO;
    }

    @Override
    public void run() {
        logger.info("------> this is run ThreadA <-------");

        commonTO.operation(0);

//        commonTO.functionShow(0);
//
//        commonTO.functionShowSynchronized(0);
//        commonTO.functionShowSynchronized(0);
//
//        // 锁重入
//        commonTO.functionShowSynchronized(1);
    }

}
