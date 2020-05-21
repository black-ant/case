package com.gang.study.springautowired.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2020/5/20 16:17
 * @Created by zengzg
 */
@Component
public class TestService implements ApplicationRunner {

    @Resource(name = "ChildGirlService")
    private IFatherService service;

    //    @Autowired
    //    private IFatherService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        service.test();
    }
}
