package com.gang.design.design3.iterator;

/**
 * @Classname Iterator
 * @Description TODO
 * @Date 2020/12/13 14:46
 * @Created by zengzg
 */
public interface Iterator {
    //前移
    public Object previous();

    //后移
    public Object next();
    public boolean hasNext();

    //取得第一个元素
    public Object first();
}
