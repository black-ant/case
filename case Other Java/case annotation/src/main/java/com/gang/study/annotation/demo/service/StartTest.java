package com.gang.study.annotation.demo.service;

import com.gang.study.annotation.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class StartTest implements ApplicationRunner {

    @Autowired
    CustomizeAnnotation customizeAnnotation;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        customizeAnnotation.getAnnoValue(new UserModel());
    }
}
