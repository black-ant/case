package com.gang.design.design3.visitor;

/**
 * @Classname VisitorMain
 * @Description TODO
 * @Date 2020/12/13 14:23
 * @Created by zengzg
 */
public class VisitorMain {

    public static void main(String[] args) {

        Visitor visitor = new MyVisitor();
        Subject sub = new MySubject();
        sub.accept(visitor);
    }
}
