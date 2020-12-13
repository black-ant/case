package com.gang.design.design3.observer;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @Classname AbstractSubject
 * @Description TODO
 * @Date 2020/12/13 14:49
 * @Created by zengzg
 */
public abstract class AbstractSubject implements Subject {

    private Vector<Observer> vector = new Vector<Observer>();

    public void add(Observer observer) {
        vector.add(observer);
    }

    public void del(Observer observer) {
        vector.remove(observer);
    }

    public void notifyObservers() {
        Enumeration<Observer> enumo = vector.elements();
        while(enumo.hasMoreElements()){
            enumo.nextElement().update();
        }
    }
}
