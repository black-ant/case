package com.myjpa.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.myjpa.demo.entity.UserEntity;
import com.myjpa.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/5/22 16:01
 * @Created by zengzg
 */
@Component
public class TestController implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<UserEntity> user = userRepository.getByUserName("gang");
        logger.info("------> this is :{} <-------", JSONObject.toJSONString(user));
    }
}
