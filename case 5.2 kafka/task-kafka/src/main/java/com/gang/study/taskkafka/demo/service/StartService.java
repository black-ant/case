package com.gang.study.taskkafka.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2019/11/1 14:01
 * @Created by ant-black 1016930479@qq.com
 */
@Service
public class StartService implements ApplicationRunner {

    @Autowired
    private KafkaProduce kafkaProduce;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        kafkaProduce.produce();
    }
}
