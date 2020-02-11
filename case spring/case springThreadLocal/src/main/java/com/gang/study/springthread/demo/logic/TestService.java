package com.gang.study.springthread.demo.logic;

import com.gang.study.springthread.demo.to.TestTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2020/2/11 11:34
 * @Created by zengzg
 */
@Component
public class TestService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() {

        logger.info("------> this is run <-------");
        for (int i = 0; i < 10; i++) {
            TestTO testTO = new TestTO(String.valueOf(i));
            testTO.setName(testTO.getName() + i);
            logger.info("------> this is testTO :{}-- thread :{} <-------", testTO.getName(), Thread.currentThread());
        }
    }

    public void run(TestTO testTO) {

        logger.info("------> this is run <-------");
        for (int i = 0; i < 10; i++) {
            Integer num = (testTO.getThreadValue()) + 1;
            testTO.setThreadValue(num);
            logger.info("------> this is testTO :{}-- thread :{} <-------", num, Thread.currentThread());
        }
    }


    public void run01(TestTO testTO) {

        logger.info("------> this is run <-------");
        for (int i = 0; i < 10; i++) {
            testTO.setName(testTO.getName() + i);
            logger.info("------> this is testTO :{}-- thread :{} <-------", testTO.getName(), Thread.currentThread());
        }
    }


}
