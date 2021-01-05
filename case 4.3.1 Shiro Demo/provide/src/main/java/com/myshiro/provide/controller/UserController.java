package com.myshiro.provide.controller;

import com.myshiro.provide.entity.User;
import com.myshiro.provide.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/21 22:46
 * @Version 1.0
 **/
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("getuser")
    public User getUser(){
        return userService.findByUsername();
    }
}
