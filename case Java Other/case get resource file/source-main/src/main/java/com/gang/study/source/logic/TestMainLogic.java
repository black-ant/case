package com.gang.study.source.logic;

import com.gang.study.source.TestLogic;
import com.gang.study.source.to.MainTO;
import com.gang.study.source.to.OneTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname TestMainLogic
 * @Description TODO
 * @Date 2020/7/3 14:54
 * @Created by zengzg
 */
@Component
public class TestMainLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestLogic logic;

    public void test() {
        logic.test();
    }

    public void path() {
        logger.info("------> this is path Main :{} <-------", MainTO.class.getClassLoader().getResource(""));
        logger.info("------> this is path One :{} <-------", OneTO.class.getClassLoader().getResource(""));

        logger.info("------> this is path Main :{} <-------", MainTO.class.getResource(""));
        logger.info("------> this is path One :{} <-------", MainTO.class.getResource(""));


        logger.info("------> this is path Main :{} <-------", MainTO.class.getClassLoader().getResource("/"));
        logger.info("------> this is path One :{} <-------", OneTO.class.getClassLoader().getResource("/"));

        logger.info("------> this is path Main :{} <-------", MainTO.class.getResource("/"));
        logger.info("------> this is path One :{} <-------", MainTO.class.getResource("/"));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        test();
        path();
    }
}
