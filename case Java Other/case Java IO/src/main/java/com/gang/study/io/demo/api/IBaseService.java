package com.gang.study.io.demo.api;

/**
 * @Classname IBaseService
 * @Description TODO
 * @Date 2020/7/30 10:04
 * @Created by zengzg
 */
public interface IBaseService<T> {

    T output(String config) throws Exception;

    void input(T t) throws Exception;
}
