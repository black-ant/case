package com.ang.study.email.demo.entity;

import lombok.Data;

@Data
public class EmailSetting {

    private String account;

    private String password;

    private String protocol;

    private String host;

    private String port;

    private String auth;

    private boolean enable;

    private int time;
}
