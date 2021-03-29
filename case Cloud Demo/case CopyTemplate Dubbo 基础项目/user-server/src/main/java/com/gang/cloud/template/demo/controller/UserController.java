package com.gang.cloud.template.demo.controller;

import com.gang.cloud.template.demo.entity.UserEntity;
import com.gang.cloud.template.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2021/3/18
 * @Created by zengzg
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("list")
    public Collection<UserEntity> list() {
        return userRepository.findAll();
    }

    @GetMapping("get/{id}")
    public UserEntity list(@PathVariable("id") String id) {
        return userRepository.getOne(id);
    }
}
