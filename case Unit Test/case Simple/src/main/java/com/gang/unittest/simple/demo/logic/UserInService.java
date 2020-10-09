package com.gang.unittest.simple.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname UserInService
 * @Description TODO
 * @Date 2020/10/7 15:16
 * @Created by zengzg
 */
@Component
public class UserInService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getUser() {
        logger.info("------> this is in UserInService getUser <-------");
        return "success UserInService";
    }
}
