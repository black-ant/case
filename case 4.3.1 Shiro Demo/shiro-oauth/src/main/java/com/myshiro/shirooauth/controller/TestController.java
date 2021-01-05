package com.myshiro.shirooauth.controller;

import com.myshiro.shirooauth.entity.Role;
import com.myshiro.shirooauth.entity.User;
import com.myshiro.shirooauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/24 21:15
 * @Version 1.0
 **/
@RestController
public class TestController {

    @Autowired
    UserService userService;

    @GetMapping("role")
    public Role findRole(){
        return userService.findRoleByRoleid();
    }

    @GetMapping("finduser")
    public User findUser(){
        return userService.findUserByUsername();
    }
}
