package com.gang.study.thread.spring.demo.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartLogic
 * @Description TODO
 * @Date 2020/4/17 13:46
 * @Created by zengzg
 */
@Component
public class StartLogic implements ApplicationRunner {


    @Autowired
    private ExampleTestLogic exampleTestLogic;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        exampleTestLogic.run();
    }
}
