package com.gang.demo;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/4/28 22:29
 * @Created by zengzg
 */
@Component
public class TestLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClassConfiguration classConfiguration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> {} <-------", classConfiguration.toString());
    }
}
