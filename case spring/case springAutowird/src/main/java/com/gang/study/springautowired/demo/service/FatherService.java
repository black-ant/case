package com.gang.study.springautowired.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname FatherService
 * @Description TODO
 * @Date 2020/5/20 16:15
 * @Created by zengzg
 */
public class FatherService implements IFatherService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void test() {
        logger.info("------> this is father <-------");
    }
}
