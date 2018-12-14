package com.security.demo.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("login")
    public JSONObject login(){
        JSONObject root = new JSONObject();
        return root;
    }
}
