package com.gang.study.myoracle.demo.logic;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.myoracle.demo.entity.OracleUser;
import com.gang.study.myoracle.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/4/6 20:08
 * @Created by zengzg
 */
@Component
public class TestLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in run <-------");
        List<OracleUser> list = userRepository.findAll();
        logger.info("------> this is size :{} <-------", list.size());
        list.forEach(item -> {
            logger.info("------> item :{}<-------", JSONObject.toJSONString(item));
        });
    }
}
