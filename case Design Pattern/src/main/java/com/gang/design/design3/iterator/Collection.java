package com.gang.design.design3.iterator;

/**
 * @Classname Collection
 * @Description TODO
 * @Date 2020/12/13 14:46
 * @Created by zengzg
 */
public interface Collection {

    public Iterator iterator();

    /*ȡ�ü���Ԫ��*/
    public Object get(int i);

    /*ȡ�ü��ϴ�С*/
    public int size();
}
