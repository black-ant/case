package com.gang.design.design3.interpreter;

/**
 * @Classname InterpreterMain
 * @Description TODO
 * @Date 2020/12/13 14:08
 * @Created by zengzg
 */
public class InterpreterMain {

    public static void main(String[] args) {

        // ¼ÆËã9+2-8µÄÖµ
        int result = new Minus().interpret((new Context(new Plus()
                .interpret(new Context(9, 2)), 8)));
        System.out.println(result);
    }
}
