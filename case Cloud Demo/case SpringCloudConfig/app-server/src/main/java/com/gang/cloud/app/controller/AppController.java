package com.gang.cloud.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname AppController
 * @Description TODO
 * @Date 2021/10/8
 * @Created by zengzg
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @Value(value = "${gang.test.name:default}")
    private String username;

    @Value(value = "${gang.test.age:10}")
    private String age;

    @GetMapping
    public String getApp() {
        return username + age;
    }
}
