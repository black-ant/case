package com.gang.jcommander.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/6/15
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in StartService <-------");

        String[] argsStr = {"-h", "127.0.0.1", "-p", "8806", "--storeMode", "test"};
//        String[] argsStr = {"-h 127.0.0.1"};
        ParameterParser parameterParser = new ParameterParser(argsStr);

        logger.info("------> 参数 :{} <-------", parameterParser.toString());


    }
}
