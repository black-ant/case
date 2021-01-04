package com.gang.study.h2spring.demo.controller;

import com.gang.study.h2spring.demo.entity.User;
import com.gang.study.h2spring.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/4/5 22:14
 * @Created by zengzg
 */
@RestController
@RequestMapping("/test")
public class TestController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @GetMapping("get/{id}")
    public String findByid(@PathVariable("id") Long id) {
        User user = userRepository.getOne(id);
        logger.info("------> this is json  <-------", user);
        return String.valueOf(user);
    }

    @GetMapping("list")
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("save/{id}")
    public String save(@PathVariable("id") Long id) {
        User user = new User();
        user.setUserId(id);
        user.setUserName("test");
        user.setCreateTime(new Date());
        this.userRepository.save(user);
        return "ok";
    }
}
