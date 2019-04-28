package com.myjpa.demo.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user")
@Data
public class UserEntity {

    @Id
    private int userid;
    private String username;
    private String usertype;
    private byte isactive;
    private String userlink;
    private String remark;
}
