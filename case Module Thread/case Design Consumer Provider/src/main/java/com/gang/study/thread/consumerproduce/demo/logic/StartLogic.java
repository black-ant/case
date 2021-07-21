package com.gang.study.thread.consumerproduce.demo.logic;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartLogic
 * @Description TODO
 * @Date 2020/4/19 13:36
 * @Created by zengzg
 */
//@Component
public class StartLogic implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {

        ProduceThead produceThead = new ProduceThead();
        produceThead.start();

        ConsumerThead consumerThead = new ConsumerThead();
        consumerThead.start();
    }
}
