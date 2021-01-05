package com.gang.design.design3.visitor;

/**
 * @Classname MySubject
 * @Description TODO
 * @Date 2020/12/13 14:22
 * @Created by zengzg
 */
public class MySubject implements Subject {

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getSubject() {
        return "love";
    }
}
