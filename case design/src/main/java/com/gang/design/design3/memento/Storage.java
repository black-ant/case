package com.gang.design.design3.memento;

/**
 * @Classname Storage
 * @Description TODO
 * @Date 2020/12/13 14:40
 * @Created by zengzg
 */
public class Storage {

    private Memento memento;

    public Storage(Memento memento) {
        this.memento = memento;
    }

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
