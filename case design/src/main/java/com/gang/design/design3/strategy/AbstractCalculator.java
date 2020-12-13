package com.gang.design.design3.strategy;

/**
 * @Classname AbstractCalculator
 * @Description TODO
 * @Date 2020/12/13 14:57
 * @Created by zengzg
 */
public abstract class AbstractCalculator {

    public int[] split(String exp,String opt){
        String array[] = exp.split(opt);
        int arrayInt[] = new int[2];
        arrayInt[0] = Integer.parseInt(array[0]);
        arrayInt[1] = Integer.parseInt(array[1]);
        return arrayInt;
    }
}
