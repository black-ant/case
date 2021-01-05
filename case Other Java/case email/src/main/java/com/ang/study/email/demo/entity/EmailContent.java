package com.ang.study.email.demo.entity;

import lombok.Data;

@Data
public class EmailContent {

    private String title;

    private String type;

    private String content;

    private String receiverUser;

}
