package com.gang.study.sso.ltpa.demo.entity;

/**
 * @Classname UserInfo
 * @Description TODO
 * @Date 2020/6/30 16:02
 * @Created by zengzg
 */
public class UserInfo {

    private String uid;

    private String userName;

    private String other;

    public UserInfo(String uid, String userName, String other) {
        this.uid = uid;
        this.userName = userName;
        this.other = other;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
