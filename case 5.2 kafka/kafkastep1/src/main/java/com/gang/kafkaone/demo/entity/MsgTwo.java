package com.gang.kafkaone.demo.entity;

import java.util.Date;

public class MsgTwo {

    private Integer id;
    private String msg;
    private Date date;

    public MsgTwo(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
        this.date = new Date();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "id:" + id + ",msg:" + msg;
    }
}
