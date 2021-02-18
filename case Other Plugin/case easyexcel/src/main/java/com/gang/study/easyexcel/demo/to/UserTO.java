package com.gang.study.easyexcel.demo.to;

import java.util.Date;

/**
 * @Classname UserTO
 * @Description TODO
 * @Date 2021/2/15 15:40
 * @Created by zengzg
 */
public class UserTO {


    private String id;

    private String username;

    private Integer age;

    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
