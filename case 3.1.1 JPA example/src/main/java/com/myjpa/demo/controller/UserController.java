package com.myjpa.demo.controller;


import com.myjpa.demo.entity.UserEntity;
import com.myjpa.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public List<UserEntity> getUser() {
        List<UserEntity> list = userService.findUser();
        logger.info("------> list :{} <-------", list);
        return list;
    }

    @GetMapping("/getname")
    public List<UserEntity> findByUsername() {
        return userService.findByUsername();
    }

}
