package com.gang.study.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * @Classname UserEntity
 * @Description TODO
 * @Date 2020/6/30 14:01
 * @Created by zengzg
 */
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", unique = true, nullable = false)
    private Integer userId;

    @Column(name = "username")
    private String userName;

    @Column(name = "user_role", updatable = false, insertable = false)
    private Integer userRole;

    @Column(name = "user_org", updatable = false, insertable = false)
    private Integer user_org;

    @OneToOne
    @JoinColumn(name = "user_role")
    private RoleEntity roleEntity;

    @OneToMany
    @JoinColumn(name = "user_org")
    private List<OrgEntity> orgEntities;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }
}
