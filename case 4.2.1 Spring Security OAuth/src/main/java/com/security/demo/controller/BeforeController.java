package com.security.demo.controller;


import com.security.demo.entity.Roles;
import com.security.demo.entity.Users;
import com.security.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("before")
public class BeforeController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;


    @RequestMapping("userregister")
    @Transactional
    public String register(
            @RequestParam("username") String username,
            @RequestParam("roles") String roles,
            @RequestParam("password") String password) {
        logger.info("username:{}--password:{}---roles:{}",username,password,roles);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encodedPassword = passwordEncoder.encode(password);
        Users user = new Users(username,encodedPassword);
        Roles role = userService.findRoles(roles);
        user.setRoles(getRolesList(role));
        userService.saveUser(user);
        return "login";
    }
    public List<Roles> getRolesList(Roles roles) {
        List<Roles> list = new LinkedList<>();
        list.add(roles);
        return list;
    }


}
