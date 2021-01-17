package com.gang.database.demo.entity;


import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@Data
public class UserEntity {

    @Id
    @Basic(optional = false)
    @NotNull
    private int userid;

    @Column(name = "username")
    private String username;

    @Column(name = "usertype")
    private String usertype;

    @Column(name = "isactive")
    private byte isactive;

    @Column(name = "userlink")
    private String userlink;

    @Column(name = "remark")
    private String remark;

    @Column(name = "orgid")
    private String orgid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public byte getIsactive() {
        return isactive;
    }

    public void setIsactive(byte isactive) {
        this.isactive = isactive;
    }

    public String getUserlink() {
        return userlink;
    }

    public void setUserlink(String userlink) {
        this.userlink = userlink;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }
}
