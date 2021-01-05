package com.gang.study.springautowired.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname ChildService
 * @Description TODO
 * @Date 2020/5/20 16:16
 * @Created by zengzg
 */
@Component
public class ChildService extends FatherService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void test() {
        logger.info("------> this is ChildService <-------");
    }
}
