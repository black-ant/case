package com.security.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.security.demo.entity.Users;
import com.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("getuser")
    public String findUserHave(){
        System.out.println("in the function");
        Users user = userService.findUserByName("gang");
        System.out.println(user.getUsername()+"---"+user.getPassword());
        return "ok";
    }

    @RequestMapping("test001")
    public String test001(){
        return "home";
    }
}
