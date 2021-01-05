package com.gang.study.webflux.demo.to;

/**
 * @Classname User
 * @Description TODO
 * @Date 2020/7/3 23:59
 * @Created by zengzg
 */
public class User {

    //用户ID
    private int userId;

    //用户姓名
    private String userName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
