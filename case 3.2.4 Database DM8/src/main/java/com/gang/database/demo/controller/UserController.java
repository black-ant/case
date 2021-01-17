package com.gang.database.demo.controller;


import com.gang.study.wycat.mycat.entity.UserEntity;
import com.gang.study.wycat.mycat.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userService;

    @GetMapping("/getAll")
    public List<UserEntity> getUser() {
        List<UserEntity> list = userService.findAll();
        logger.info("------> list :{} <-------", list);
        return list;
    }

    @GetMapping("/getname")
    public List<UserEntity> findByUsername(@RequestParam("username") String username) {
        return userService.getByUserName(username);
    }
}
