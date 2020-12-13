package com.gang.design.design3.chain;

/**
 * @Classname ChainMain
 * @Description TODO
 * @Date 2020/12/13 14:46
 * @Created by zengzg
 */
public class ChainMain {


    public static void main(String[] args) {
        MyHandler h1 = new MyHandler("h1");
        MyHandler h2 = new MyHandler("h2");
        MyHandler h3 = new MyHandler("h3");

        h1.setHandler(h2);
        h2.setHandler(h3);

        h1.operator();
    }
}
