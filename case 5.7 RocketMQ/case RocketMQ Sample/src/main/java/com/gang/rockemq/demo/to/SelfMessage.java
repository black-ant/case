package com.gang.rockemq.demo.to;

import java.io.Serializable;

/**
 * @Classname Message
 * @Description TODO
 * @Date 2021/9/8
 * @Created by zengzg
 */
public class SelfMessage<T> implements Serializable {

    private String id;
    private T content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
