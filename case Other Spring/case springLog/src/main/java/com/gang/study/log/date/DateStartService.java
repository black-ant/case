package com.gang.study.log.date;

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
//public class DateStartService  implements ApplicationRunner{
public class DateStartService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in run <-------");
        doLog();

    }

    public void doLog() {
        for (int i = 0; i < 3000; i++) {
            logger.info("------> this is in  StartService<-------");
        }
    }

    public void doLogTrach() {
        throw new RuntimeException("测试 log");
    }
}
