package com.gang.study.stream.stram;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Stream 基础操作
 * 包含
 * 冒号 运算符
 * 箭头表达式
 */
public class BaseStream {

    public static void execute(BaseInteface worker) {
        worker.baseFunction("111");
    }

    public void baseFunction(String s) {
        happen(1000, (m) -> System.out.println("一共消费" + m + "元"));
        execute((data) -> System.out.println(data));
    }


    public void happen(double money, Consumer<Double> con) {
        con.accept(money);
    }

    public <T extends Collection> void doubleColon(T t) {
        t.stream().forEach(System.out::println);
    }




}
