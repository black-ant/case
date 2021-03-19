package com.gang.study.rmi.demo.to;

import java.io.Serializable;

/**
 * @Classname InnerServerTO
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
public class InnerServerTO implements Serializable {

    private String userName;

    private Integer userAge;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }
}
