package com.gang.cloud.to;

import java.io.Serializable;

/**
 * @Classname User
 * @Description TODO
 * @Date 2020/9/29 22:28
 * @Created by zengzg
 */
public class User implements Serializable {

    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
