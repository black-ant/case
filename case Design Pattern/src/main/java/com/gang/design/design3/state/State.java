package com.gang.design.design3.state;

/**
 * @Classname State
 * @Description TODO
 * @Date 2020/12/13 14:38
 * @Created by zengzg
 */
public class State {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void method1(){
        System.out.println("execute the first opt!");
    }

    public void method2(){
        System.out.println("execute the second opt!");
    }
}
