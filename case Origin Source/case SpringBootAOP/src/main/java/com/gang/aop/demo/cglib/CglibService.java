package com.gang.aop.demo.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname CglibService
 * @Description TODO
 * @Date 2021/10/6
 * @Created by zengzg
 */
public class CglibService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() {
        logger.info("------> this is CglibService run <-------");
    }
}
