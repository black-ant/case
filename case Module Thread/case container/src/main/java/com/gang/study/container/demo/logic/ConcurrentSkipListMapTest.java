package com.gang.study.container.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Classname ConcurrentSkipListMapTest
 * @Description TODO
 * @Date 2021/2/19 10:38
 * @Created by zengzg
 */
//@Service
public class ConcurrentSkipListMapTest implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ConcurrentSkipListMap hashMap = new ConcurrentSkipListMap();
    }
}

