package com.gang.design.design3.iterator;

/**
 * @Classname Iterator
 * @Description TODO
 * @Date 2020/12/13 14:46
 * @Created by zengzg
 */
public interface Iterator {
    //ǰ��
    public Object previous();

    //����
    public Object next();
    public boolean hasNext();

    //ȡ�õ�һ��Ԫ��
    public Object first();
}
