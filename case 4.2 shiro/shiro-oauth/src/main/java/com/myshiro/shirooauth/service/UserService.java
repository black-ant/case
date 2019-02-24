package com.myshiro.shirooauth.service;

import com.myshiro.shirooauth.entity.Role;
import com.myshiro.shirooauth.entity.User;
import com.myshiro.shirooauth.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/24 18:36
 * @Version 1.0
 **/
@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserMapper userMapper;

    public User findByUsername(){
        return userMapper.findByUsername("gang");
    }

    public Role findRoleByRoleid(){
        logger.info("根据id 查询 Role>>>>");
        return userMapper.findRolesByRoleid(1);
    }

    public User findUserByUsername(){
        logger.info("根据id 查询 Role>>>>");
        return userMapper.findByUsernametest("gang");
    }
}

