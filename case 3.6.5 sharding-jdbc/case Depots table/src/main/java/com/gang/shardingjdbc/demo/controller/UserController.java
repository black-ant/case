package com.gang.shardingjdbc.demo.controller;

import com.gang.shardingjdbc.demo.entity.api.User;
import com.gang.shardingjdbc.demo.repository.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("save")
    public void save() {
        User user = new User();
        user.setUserId(1);
        user.setUserName("dalaoyang");
        user.setPwd("beijing");
//        userRepository.(user);
    }


    @GetMapping("save/{key}")
    public void saveOne(@PathVariable(name = "key") Integer key) {
        User user = new User();
        user.setUserId(key);
        user.setUserName("dalaoyang");
        user.setPwd("beijing");
//        userRepository.save(user);
    }
//
//    @GetMapping("getAll")
//    public Object getAll() {
//        return userRepository.findAll();
//    }
//
//    @GetMapping("saveList")
//    public void saveList() {
//        for (int i = 0; i < 30; i++) {
//            User user = new User();
//            user.setId(i);
//            user.setName("dalaoyang");
//            user.setCity("beijing");
//            userRepository.save(user);
//        }
//    }
}
