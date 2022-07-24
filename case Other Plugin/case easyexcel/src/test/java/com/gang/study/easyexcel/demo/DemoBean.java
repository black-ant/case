package com.gang.study.easyexcel.demo;

/**
 * @Classname DemoBean
 * @Description TODO
 * @Date 2022/2/25
 * @Created by zengzg
 */
public class DemoBean {

    public DemoBean() {
    }

    public DemoBean(String username) {
        this.username = username;
    }

    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

