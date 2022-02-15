package com.myjpa.demo.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "user_1")
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
//
//    @OneToOne
//    @JoinColumn(name = "userid", insertable = false, updatable = false)
//    private OrgEntity orglist;
}
