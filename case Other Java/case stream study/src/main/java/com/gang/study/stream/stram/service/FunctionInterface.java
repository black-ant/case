package com.gang.study.stream.stram.service;

/**
 * @Classname FunctionInterface
 * @Description TODO
 * @Date 2021/7/7
 * @Created by zengzg
 */
@FunctionalInterface
public interface FunctionInterface<T, D> {

    /**
     * 函数接口抽象方法 , 最终执行方法
     * PS : 至此泛型
     */
    T invoke(D input);

    /**
     * Object 中方法 ,该方法不违背函数式编程原则
     *
     * @param var1
     * @return
     */
    boolean equals(Object var1);

    /**
     * default不是抽象方法 , 不违背原则
     */
    default void defaultMethod() {
    }

    /**
     * static不是抽象方法
     */
    static void staticMethod(String msg) {
        System.out.println(msg);
    }

}
