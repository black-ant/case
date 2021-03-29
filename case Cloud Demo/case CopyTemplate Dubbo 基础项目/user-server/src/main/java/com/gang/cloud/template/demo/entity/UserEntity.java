package com.gang.cloud.template.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Classname UserEntity
 * @Description TODO
 * @Date 2021/3/18
 * @Created by zengzg
 */
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    private Integer userid;

    @Column(name = "username")
    private String username;

    @Column(name = "user_org")
    private Integer userOrg;

    @Column(name = "user_role")
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
