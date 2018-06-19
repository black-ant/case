package com.springboot2.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "jsf1", catalog = "")
public class UserEntity {
    private int userid;
    private String username;
    private String usertype;
    private byte isactive;
    private String userlink;
    private Timestamp createtime;
    private String remark;

    @Id
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "usertype")
    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    @Basic
    @Column(name = "isactive")
    public byte getIsactive() {
        return isactive;
    }

    public void setIsactive(byte isactive) {
        this.isactive = isactive;
    }

    @Basic
    @Column(name = "userlink")
    public String getUserlink() {
        return userlink;
    }

    public void setUserlink(String userlink) {
        this.userlink = userlink;
    }

    @Basic
    @Column(name = "createtime")
    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userid == that.userid &&
                isactive == that.isactive &&
                Objects.equals(username, that.username) &&
                Objects.equals(usertype, that.usertype) &&
                Objects.equals(userlink, that.userlink) &&
                Objects.equals(createtime, that.createtime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userid, username, usertype, isactive, userlink, createtime, remark);
    }
}
