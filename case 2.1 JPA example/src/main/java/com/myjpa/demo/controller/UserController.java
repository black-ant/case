package com.myjpa.demo.controller;


import com.myjpa.demo.entity.UserEntity;
import com.myjpa.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public void getUser() {
        userService.findUser();
    }

    @GetMapping("/getname")
    public List<UserEntity> findByUsername() {
        return  userService.findByUsername();
    }
}
