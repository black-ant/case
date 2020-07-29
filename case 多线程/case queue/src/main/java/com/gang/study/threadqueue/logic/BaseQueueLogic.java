package com.gang.study.threadqueue.logic;

import com.gang.study.threadqueue.to.UserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;

import java.util.Random;

/**
 * @Classname BaseQueueLogic
 * @Description TODO
 * @Date 2020/7/23 17:56
 * @Created by zengzg
 */
public abstract class BaseQueueLogic implements ApplicationRunner {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserTO createUserTO() {
        UserTO userTO = new UserTO();
        Integer num = new Random().nextInt(9999);
        userTO.setUserid(num);
        userTO.setUsername("GANG" + num);
        return userTO;
    }
}
