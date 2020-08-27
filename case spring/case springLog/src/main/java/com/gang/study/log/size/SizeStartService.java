package com.gang.study.log.size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2020/8/27 11:03
 * @Created by zengzg
 */
@Component
//public class SizeStartService implements ApplicationRunner {
public class SizeStartService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> SizeStartService this is in run <-------");
        doLog();

    }

    public void doLog() {
        for (int i = 0; i < 3000; i++) {
            logger.info("------> this is in  StartService<-------");
        }
    }
}
