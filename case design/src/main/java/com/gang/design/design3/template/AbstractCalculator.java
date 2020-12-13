package com.gang.design.design3.template;

/**
 * @Classname AbstractCalculator
 * @Description TODO
 * @Date 2020/12/13 14:56
 * @Created by zengzg
 */
public abstract class AbstractCalculator {

    /*��������ʵ�ֶԱ������������ĵ���*/
    public final int calculate(String exp,String opt){
        int array[] = split(exp,opt);
        return calculate(array[0],array[1]);
    }

    /*��������д�ķ���*/
    abstract public int calculate(int num1,int num2);

    public int[] split(String exp,String opt){
        String array[] = exp.split(opt);
        int arrayInt[] = new int[2];
        arrayInt[0] = Integer.parseInt(array[0]);
        arrayInt[1] = Integer.parseInt(array[1]);
        return arrayInt;
    }
}

