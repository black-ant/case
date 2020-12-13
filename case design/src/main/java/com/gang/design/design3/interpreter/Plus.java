package com.gang.design.design3.interpreter;

/**
 * @Classname Plus
 * @Description TODO
 * @Date 2020/12/13 14:08
 * @Created by zengzg
 */
public class Plus implements Expression {

    public int interpret(Context context) {
        return context.getNum1()+context.getNum2();
    }
}
