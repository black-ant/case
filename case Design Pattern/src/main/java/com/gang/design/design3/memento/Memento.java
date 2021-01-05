package com.gang.design.design3.memento;

/**
 * @Classname Memento
 * @Description TODO
 * @Date 2020/12/13 14:40
 * @Created by zengzg
 */
public class Memento {

    private String value;

    public Memento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
