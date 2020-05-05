package com.gang.study.common.cache.demo.logic;

import com.gang.common.cache.common.CacheInvoke;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/5/3 11:48
 * @Created by zengzg
 */
@Component
public class TestLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CacheInvoke cacheInvoke;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        cacheInvoke.set("test0001", "gang1111");
        logger.info("------> this is set over  <-------");
        cacheInvoke.get("test0001");
    }
}
