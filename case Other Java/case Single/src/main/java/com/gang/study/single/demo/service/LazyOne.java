package com.gang.study.single.demo.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname LazyOne
 * @Description 懒汉式单例模式
 * @Date 2021/2/20 16:41
 * @Created by zengzg
 */
@Service
public class LazyOne implements ApplicationRunner {

    private static Singleton singleton;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getInstance();
    }

    public static synchronized Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

}
