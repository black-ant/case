package com.gang.mongodb.demo.controller;

import com.gang.mongodb.demo.entity.User;
import com.gang.mongodb.demo.repository.UserRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2021/9/21
 * @Created by zengzg
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepositoryImpl repository;

    @GetMapping("save")
    public String save() {
        logger.info("------> this is in createUser <-------");
        repository.saveUser(new User(new Random().nextLong(), "Test", "123456"));
        return "sccuess";
    }

    @GetMapping("list")
    public List<User> list() {
        logger.info("------> this is in createUser <-------");
        Comparable
        return repository.findAll();
    }

}
