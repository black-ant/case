package com.gang.study.source.springboot.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2021/4/12
 * @Created by zengzg
 */
@Component
public class TestService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OtherService otherService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("ABCDEa123abc123".hashCode());
//        System.out.println("ABCDFB123abc123".hashCode());
//        System.out.println("ABCDEC123abc123".hashCode());

        otherService.showInfo();
    }
}
