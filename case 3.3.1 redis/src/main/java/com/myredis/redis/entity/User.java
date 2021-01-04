package com.myredis.redis.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Classname User
 * @Description TODO
 * @Date 2020/1/10 15:08
 * @Created by zengzg
 */
@Data
public class User {

    private String name;
    private Date time;
    private Double age;
    private Long code;

    public User() {
    }

    public User(String name, Date time, Double age, Long code) {
        this.name = name;
        this.time = time;
        this.age = age;
        this.code = code;
    }
}
