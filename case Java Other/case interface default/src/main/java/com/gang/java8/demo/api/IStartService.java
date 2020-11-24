package com.gang.java8.demo.api;

/**
 * @Classname IStartService
 * @Description TODO
 * @Date 2020/11/23 11:50
 * @Created by zengzg
 */
public interface IStartService {

    default String run1() {
        return "success;";
    }

    String start();
}
