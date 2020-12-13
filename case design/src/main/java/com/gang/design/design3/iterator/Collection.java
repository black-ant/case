package com.gang.design.design3.iterator;

/**
 * @Classname Collection
 * @Description TODO
 * @Date 2020/12/13 14:46
 * @Created by zengzg
 */
public interface Collection {

    public Iterator iterator();

    /*取得集合元素*/
    public Object get(int i);

    /*取得集合大小*/
    public int size();
}
