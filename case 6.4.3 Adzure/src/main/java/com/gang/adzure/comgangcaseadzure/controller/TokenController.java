package com.gang.adzure.comgangcaseadzure.controller;

import com.gang.adzure.comgangcaseadzure.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TokenController
 * @Description TODO
 * @Date 2020/3/30 14:35
 * @Created by zengzg
 */
@RestController
@RequestMapping("/unauth/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("userinfo")
    public String userinfo(@RequestParam("username") String username, @RequestParam("password") String password) {
        String userinfo = "";
        try {
            userinfo = tokenService.getToken(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            userinfo = e.getMessage();
        }
        return userinfo;
    }
}
