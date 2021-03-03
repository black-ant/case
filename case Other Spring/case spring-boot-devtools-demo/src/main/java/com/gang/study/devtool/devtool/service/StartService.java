package com.gang.study.devtool.devtool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname StartService
 * @Description 手动修改Class 即可实现
 * @Date 2019/12/3 23:01
 * @Created by zengzg
 */
@Service
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in run 33<-------");
        test();
    }

    public void test() {
        logger.info("------> test in 111 okokok<-------");
    }

}
