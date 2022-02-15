package com.mybatistest.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mybatistest.demo.entity.User;
import com.mybatistest.demo.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2021/11/3
 * @Created by zengzg
 */
@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;


    @GetMapping("user")
    public void getUser() {
        List<User> users = userMapper.selectAll();
        logger.info("------> this :{} <-------", JSONObject.toJSONString(users));
    }
}
