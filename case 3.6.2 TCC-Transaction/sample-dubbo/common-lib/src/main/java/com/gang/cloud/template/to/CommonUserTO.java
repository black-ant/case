package com.gang.cloud.template.to;

import java.io.Serializable;

/**
 * @Classname UserEntity
 * @Description TODO
 * @Date 2021/3/18
 * @Created by zengzg
 */
public class CommonUserTO implements Serializable {

    private Integer userid;

    private String username;

    private Integer userOrg;

    private Integer userRole;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserOrg() {
        return userOrg;
    }

    public void setUserOrg(Integer userOrg) {
        this.userOrg = userOrg;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }
}
