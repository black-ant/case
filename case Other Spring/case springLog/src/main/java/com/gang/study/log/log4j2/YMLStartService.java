package com.gang.study.log.log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname YMLStartService
 * @Description TODO
 * @Date 2020/8/27 13:40
 * @Created by zengzg
 */
@Service
public class YMLStartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> YMLStartService this is in run <-------");
        doLog();

    }

    public void doLog() {
        for (int i = 0; i < 3000; i++) {
            logger.info("------> YMLStartService this is in  :{}<-------", i);
        }
    }

}
