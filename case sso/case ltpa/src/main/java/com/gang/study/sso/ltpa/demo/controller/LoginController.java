package com.gang.study.sso.ltpa.demo.controller;

import com.gang.study.sso.ltpa.demo.logic.LtpaAuthLogic;
import com.gang.study.sso.ltpa.demo.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname LoginController
 * @Description TODO
 * @Date 2020/7/1 16:55
 * @Created by zengzg
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LtpaAuthLogic ltpaAuthLogic;

    @GetMapping(value = "/doLogin", produces = "application/json")
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = ltpaAuthLogic.doLogin();
        CookieUtils.setCookie(request, response, "ssoLoginToken", token, 36000);
        return "login success";
    }
}
