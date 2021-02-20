package com.gang.study.single.demo.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname LazyDclService
 * @Description 懒汉式 DCL 检查 , 该方式不正确
 * @Date 2021/2/20 16:44
 * @Created by zengzg
 */
@Service
public class LazyDclService implements ApplicationRunner {

    private static Singleton singleton;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        getInstance();
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    /**
     * 正确方式
     *
     * @return
     */
    private volatile static Singleton singletonOne;

    public static Singleton getInstanceOne() {
        if (singletonOne == null) {
            synchronized (Singleton.class) {
                if (singletonOne == null) {
                    singletonOne = new Singleton();
                }
            }
        }
        return singletonOne;
    }


    private static class SingletonHolder {
        public static Singleton singleton = new Singleton();
    }

    public static Singleton getInstanceTwo() {
        return SingletonHolder.singleton;
    }
}

}
