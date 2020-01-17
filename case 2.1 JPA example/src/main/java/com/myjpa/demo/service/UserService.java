package com.myjpa.demo.service;

import com.myjpa.demo.entity.UserEntity;
import com.myjpa.demo.repository.UserRepository;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    public List<UserEntity> findUser() {
        logger.info("------> this is find User <-------");
        return userRepository.findAll();
    }

    public List<UserEntity> findByUsername() {
        logger.info("------> this is  findByUsername<-------");
        return userRepository.getByUserName("gang");
    }

}
