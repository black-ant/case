package com.myjpa.demo.service;

import com.myjpa.demo.entity.UserEntity;
import com.myjpa.demo.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/28 22:03
 * @Version 1.0
 **/
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserEntity> findUser() {
        return userRepository.findAll();
    }

}
