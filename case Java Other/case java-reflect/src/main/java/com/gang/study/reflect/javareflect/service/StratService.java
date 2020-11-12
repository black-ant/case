package com.gang.study.reflect.javareflect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class StratService implements ApplicationRunner {

    @Autowired
    ReflectService reflectService;

    @Autowired
    FliedReflectService fliedReflectService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        reflectService.reflectGetEntity();
//        fliedReflectService.run();
    }
}
