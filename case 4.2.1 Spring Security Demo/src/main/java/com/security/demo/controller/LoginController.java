package com.security.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.security.demo.entity.UserLog;
import com.security.demo.entity.Users;
import com.security.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserService userService;

    @RequestMapping("getuser")
    public String findUserHave() {
        System.out.println("in the function");
        Users user = userService.findUserByName("gang");
        System.out.println(user.getUsername() + "---" + user.getPassword());
        return "ok";
    }

    @RequestMapping("test001")
    public String test001() {
        return "home";
    }

    @RequestMapping("getlog")
    public String findLog() {
        UserLog log = userService.findLogBySn();
        System.out.println(log.getUsername() + "---" + log.getLogdesc());
        return "home";
    }

    @RequestMapping("getUsersn")
    public String getUsersn() {
        Users user = userService.findBySn();
        System.out.println(user.getUsername() + "---" + user.getRoles());
        return "getUsersn";
    }

    @RequestMapping("saveuser")
    public Users saveuser() {
        Users user = userService.findUserByName("gang");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encodedPassword = passwordEncoder.encode(user.getPassword().trim());
        user.setPassword(encodedPassword);
        userService.saveUser(user);
        return user;
    }

    @RequestMapping("usermessage")
    public void usermessage() {
        UserDetails users = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        logger.debug("----:{}",users);
//        logger.debug("userDetails:{}--{}--{}",userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
    }
}
