package com.gang.study.cloud.baseservice.demo.service;

import com.gang.study.cloud.baseservice.demo.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2020/4/22 17:27
 * @Created by zengzg
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @GetMapping("/get/{key}")
    public User get(@PathVariable("key") String key) {
        return new User(key, "gang", 1, "user info");
    }
}
