package com.gang.study.source.springboot.demo.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname BeanStartService
 * @Description TODO
 * @Date 2021/4/20
 * @Created by zengzg
 */
@Component
public class BeanStartService implements ApplicationRunner {

    @Autowired
    private BeanAService beanAService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        beanAService.getInfo();
    }
}
