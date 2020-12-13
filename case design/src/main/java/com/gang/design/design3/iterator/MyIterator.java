package com.gang.design.design3.iterator;

/**
 * @Classname MyIterator
 * @Description TODO
 * @Date 2020/12/13 14:47
 * @Created by zengzg
 */
public class MyIterator implements Iterator {

    private Collection collection;
    private int pos = -1;

    public MyIterator(Collection collection){
        this.collection = collection;
    }

    public Object previous() {
        if(pos > 0){
            pos--;
        }
        return collection.get(pos);
    }

    public Object next() {
        if(pos<collection.size()-1){
            pos++;
        }
        return collection.get(pos);
    }

    public boolean hasNext() {
        if(pos<collection.size()-1){
            return true;
        }else{
            return false;
        }
    }

    public Object first() {
        pos = 0;
        return collection.get(pos);
    }

}
