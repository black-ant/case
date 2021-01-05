package com.gang.study.springautowired.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname ChildGirlService
 * @Description TODO
 * @Date 2020/5/20 16:18
 * @Created by zengzg
 */
@Component(value = "ChildGirlService")
public class ChildGirlService extends FatherService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void test() {
        logger.info("------> this is ChildGirlService <-------");
    }
}
