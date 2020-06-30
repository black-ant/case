package com.gang.study.sso.ltpa.demo.to;

/**
 * @Classname UserMetadata
 * @Description TODO
 * @Date 2020/6/30 16:23
 * @Created by zengzg
 */
public class UserMetadata {

    private String username;

    private String otherInfo;

    public UserMetadata(String token, String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
}
