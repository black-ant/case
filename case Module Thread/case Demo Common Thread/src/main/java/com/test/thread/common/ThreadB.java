package com.test.thread.common;

import com.test.thread.to.CommonTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname ThreadB
 * @Description TODO
 * @Date 2021/2/28 19:52
 * @Created by zengzg
 */
public class ThreadB extends Thread {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CommonTO commonTO;

    public ThreadB(CommonTO commonTO) {
        super();
        this.commonTO = commonTO;
    }

    @Override
    public void run() {
        logger.info("------> this is run ThreadB <-------");
        commonTO.operation(1);

    }
}
