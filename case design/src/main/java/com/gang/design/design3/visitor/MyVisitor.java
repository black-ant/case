package com.gang.design.design3.visitor;


/**
 * @Classname MyVisitor
 * @Description TODO
 * @Date 2020/12/13 14:22
 * @Created by zengzg
 */
public class MyVisitor implements Visitor {

    public void visit(Subject sub) {
        System.out.println("visit the subject£º"+sub.getSubject());
    }
}
