package com.gang.design.design3.visitor;

/**
 * @Classname Subject
 * @Description TODO
 * @Date 2020/12/13 14:22
 * @Created by zengzg
 */
public interface Subject {
    public void accept(Visitor visitor);
    public String getSubject();
}
