package com.gang.design.design3.chain;

/**
 * @Classname MyHandler
 * @Description TODO
 * @Date 2020/12/13 14:45
 * @Created by zengzg
 */
public class MyHandler extends AbstractHandler implements Handler {

    private String name;

    public MyHandler(String name) {
        this.name = name;
    }

    public void operator() {
        System.out.println(name+"deal!");
        if(getHandler()!=null){
            getHandler().operator();
        }
    }
}

