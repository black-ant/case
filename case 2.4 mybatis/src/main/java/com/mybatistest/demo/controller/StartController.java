package com.mybatistest.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.mybatistest.demo.service.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StartController {

    @Autowired
    loginService loginService;

    @RequestMapping("send")
    @ResponseBody
    public JSONObject login(@RequestParam("username") String username, @RequestParam("password") String password) {
        JSONObject root = new JSONObject();
        boolean isok  = loginService.canLogin(username,password);
        root.put("status",isok);
        return root;
    }
}
