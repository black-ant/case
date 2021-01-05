package com.gang.design.design3.iterator;

/**
 * @Classname IteratorMain
 * @Description TODO
 * @Date 2020/12/13 14:47
 * @Created by zengzg
 */
public class IteratorMain {

    public static void main(String[] args) {
        Collection collection = new MyCollection();
        Iterator it = collection.iterator();

        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}
