package com.gang.shardingjdbc.demo.controller;

import com.gang.shardingjdbc.demo.entity.User;
import com.gang.shardingjdbc.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class UserController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("save")
    public void save() {
        User user = new User();
        user.setId(100L);
        user.setName("dalaoyang");
        user.setCity("beijing");
        userRepository.save(user);
    }

    @GetMapping("getAll")
    public Object getAll() {
        return userRepository.findAll();
    }
}
