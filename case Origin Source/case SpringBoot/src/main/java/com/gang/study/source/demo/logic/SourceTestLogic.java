package com.gang.study.source.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname SourceTestLogic
 * @Description TODO
 * @Date 2020/7/8 16:25
 * @Created by zengzg
 */
@Component
public class SourceTestLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> run <-------");
    }
}
