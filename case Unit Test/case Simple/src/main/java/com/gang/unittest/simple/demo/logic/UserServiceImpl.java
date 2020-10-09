package com.gang.unittest.simple.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname UserServiceImpl
 * @Description TODO
 * @Date 2020/10/7 15:10
 * @Created by zengzg
 */
@Component
public class UserServiceImpl implements IUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInService userInService;

    @Override
    public String getUser() {
        logger.info("------> this is in user <-------");
        userInService.getUser();
        return "success";
    }
}
