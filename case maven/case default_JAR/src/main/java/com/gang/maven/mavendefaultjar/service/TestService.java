package com.gang.maven.mavendefaultjar.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.gang.base.controller.UserController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2020/11/3 9:52
 * @Created by zengzg
 */
@Component
public class TestService implements ApplicationRunner {

    @Autowired
    private UserController baseController;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        baseController.create(null);
    }
}
