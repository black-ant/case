package com.gang.java8.demo.api;

/**
 * @Classname ITestService
 * @Description TODO
 * @Date 2020/11/23 11:50
 * @Created by zengzg
 */
public interface ITestService {

    default String run2() {
        return "success;";
    }
}
