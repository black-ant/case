package com.mybatistest.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private Integer sn;

    private String username;

    private String password;

    private String power;

    private String utype;

    private Boolean isactive;

    private static final long serialVersionUID = 1L;


}