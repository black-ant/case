package com.gang.study.skywalking.demo.entity;

import java.io.Serializable;

/**
 * @Classname Org
 * @Description TODO
 * @Date 2020/11/21 14:22
 * @Created by zengzg
 */
public class Org implements Serializable {

    private static final long serialVersionUID = 610105280927740076L;

    private String userid;

    public Org(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
