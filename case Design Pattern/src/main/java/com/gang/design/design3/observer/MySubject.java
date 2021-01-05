package com.gang.design.design3.observer;

/**
 * @Classname MySubject
 * @Description TODO
 * @Date 2020/12/13 14:49
 * @Created by zengzg
 */
public class MySubject extends AbstractSubject {

    public void operation() {
        System.out.println("update self!");
        notifyObservers();
    }

}
