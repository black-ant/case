package com.mybatistest.demo.service;


import com.mybatistest.demo.entity.User;
import com.mybatistest.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class loginService {

    @Autowired
    UserMapper userMapper;

    public boolean canLogin(String username,String password){
        User user = userMapper.findByFilter(username);
        if(null!=user&&"666666".equals(user.getPassword())){
            return true;
        }
        return false;
    }
}
