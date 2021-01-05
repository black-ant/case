package com.gang.study.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/7/3 14:52
 * @Created by zengzg
 */
@Component
public class TestLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     */
    public void test() {
        logger.info("------> this is in TestLogic <-------");
    }

}
