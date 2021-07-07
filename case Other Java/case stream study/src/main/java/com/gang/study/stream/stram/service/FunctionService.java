package com.gang.study.stream.stram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname FunctionService
 * @Description TODO
 * @Date 2021/7/6
 * @Created by zengzg
 */
@Component
public class FunctionService extends SuperFunctionService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        testFunction();
//
//        testDoubleFunction();
    }

    public void testFunction() {
        logger.info("------> [执行函数式方法] <-------");

        // 方法一 : 传入代码块
        invokeInterface((input) -> {
            return input + "-output1";
        });

        // 方法二 : 直接访问
        invokeInterface((input) -> input + "-output2");

        // 方法三 : 传入对象
        FunctionInterface<String, String> funtion = (input) -> input + "-output3";
        invokeInterface(funtion);

    }


    public void invokeInterface(FunctionInterface<String, String> funtion) {
        String response = funtion.invoke("test");
        logger.info("------> this is output :{} <-------", response);
    }

    public void testDoubleFunction() {

        // 案例一 : 传入实例方法
        Consumer consumer = System.out::println;
        invokeDoubleFunction(consumer);

        // 案例二 : 传入静态方法
        Consumer<String> consumer2 = FunctionInterface::staticMethod;
        invokeDoubleFunction(consumer2);

        // 案例三 : 传入超类
        Consumer<String> consumer3 = super::superMethod;
        invokeDoubleFunction(consumer3);

        // 案例四 : 传入构造函数
//        Consumer<ArrayList> consumer4 = ArrayList::new;
//        invokeDoubleFunction2(consumer4);

    }


    public void invokeDoubleFunction(Consumer consumer) {
        Stream<String> stream = Stream.of("aaa", "bbb", "ddd", "ccc", "fff");
        stream.forEach(consumer);
    }

    public void invokeDoubleFunction2(Consumer consumer) {
        Set set = new TreeSet();
        set.add("aaa");
        set.add("bbb");

        Stream<Collection<String>> stream = Stream.of(set);
        stream.forEach(consumer);
    }


}
