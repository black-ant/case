package com.security.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("manage")
public class UserManageContoller {

    @RequestMapping("test001")
    public String test001(){
        return "home";
    }
}
