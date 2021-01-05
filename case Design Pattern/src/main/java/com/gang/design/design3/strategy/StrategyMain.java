package com.gang.design.design3.strategy;

/**
 * @Classname StrategyMain
 * @Description TODO
 * @Date 2020/12/13 14:57
 * @Created by zengzg
 */
public class StrategyMain {

    public static void main(String[] args) {
        String exp = "2+8";
        ICalculator cal = new Plus();
        int result = cal.calculate(exp);
        System.out.println(result);
    }
}
