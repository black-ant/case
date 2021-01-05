package com.gang.design.design3.iterator;

/**
 * @Classname MyCollection
 * @Description TODO
 * @Date 2020/12/13 14:47
 * @Created by zengzg
 */
public class MyCollection implements Collection {

    public String string[] = {"A","B","C","D","E"};

    public Iterator iterator() {
        return new MyIterator(this);
    }

    public Object get(int i) {
        return string[i];
    }

    public int size() {
        return string.length;
    }
}
