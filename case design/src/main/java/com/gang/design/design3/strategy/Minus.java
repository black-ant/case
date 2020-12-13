package com.gang.design.design3.strategy;

/**
 * @Classname Minus
 * @Description TODO
 * @Date 2020/12/13 14:58
 * @Created by zengzg
 */
public class Minus extends AbstractCalculator implements ICalculator {

    public int calculate(String exp) {
        int arrayInt[] = split(exp,"-");
        return arrayInt[0]-arrayInt[1];
    }

}
