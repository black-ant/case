package com.gang.study.multiplysource.entity;


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

//    @OneToOne
//    @JoinColumn(name = "userid", insertable = false, updatable = false)
//    private OrgEntity orglist;
}
