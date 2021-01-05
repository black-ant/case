package com.gang.design.design3.observer;

/**
 * @Classname ObserverMain
 * @Description TODO
 * @Date 2020/12/13 14:55
 * @Created by zengzg
 */
public class ObserverMain {

    public static void main(String[] args) {
        Subject sub = new MySubject();
        sub.add(new Observer1());
        sub.add(new Observer2());

        sub.operation();
    }
}
