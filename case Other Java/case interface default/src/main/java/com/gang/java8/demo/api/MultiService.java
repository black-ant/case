package com.gang.java8.demo.api;

/**
 * @Classname MultiInterface
 * @Description TODO
 * @Date 2021/7/30
 * @Created by zengzg
 */
public interface MultiService {

    void test();

    interface MultiInnerInterface {

        void innerTest();
    }
}
