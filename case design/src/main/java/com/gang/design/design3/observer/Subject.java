package com.gang.design.design3.observer;

/**
 * @Classname Subject
 * @Description TODO
 * @Date 2020/12/13 14:49
 * @Created by zengzg
 */
public interface Subject {

    /*���ӹ۲���*/
    public void add(Observer observer);

    /*ɾ���۲���*/
    public void del(Observer observer);

    /*֪ͨ���еĹ۲���*/
    public void notifyObservers();

    /*����Ĳ���*/
    public void operation();
}
