package com.gang.design.design3.memento;

/**
 * @Classname Original
 * @Description TODO
 * @Date 2020/12/13 14:40
 * @Created by zengzg
 */
public class Original {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Original(String value) {
        this.value = value;
    }

    public Memento createMemento(){
        return new Memento(value);
    }

    public void restoreMemento(Memento memento){
        this.value = memento.getValue();
    }
}
