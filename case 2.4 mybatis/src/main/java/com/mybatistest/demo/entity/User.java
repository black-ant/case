package com.mybatistest.demo.entity;

import com.mybatistest.demo.util.UUIdGenId;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {


    private String sn;

    private String username;

    private String password;

    private String power;

    private String utype;

    private Boolean isactive;

    private String remarks;

    private static final long serialVersionUID = 1L;


}