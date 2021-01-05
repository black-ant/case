package com.myshiro.provide.service;

import com.myshiro.provide.entity.User;
import com.myshiro.provide.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/21 22:46
 * @Version 1.0
 **/
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User findByUsername(){
        return userMapper.findByUsername("gang");
    }
}
