package com.gang.cloud.dubbo.to;

/**
 * @Classname UserTO
 * @Description TODO
 * @Date 2020/9/30 17:01
 * @Created by zengzg
 */
public class UserTO {

    private String username;

    public UserTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
