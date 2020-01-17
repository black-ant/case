package com.gang.kafkaone.demo.entity;

import lombok.Data;

@Data
public class MsgOne {

    private Integer id;
    private String msg;

    public MsgOne(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "id:" + id + ",msg:" + msg;
    }
}
