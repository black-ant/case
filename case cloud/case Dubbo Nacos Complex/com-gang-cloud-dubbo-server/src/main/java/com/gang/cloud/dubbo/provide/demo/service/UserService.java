package com.gang.cloud.dubbo.provide.demo.service;

import com.gang.cloud.dubbo.service.IUserService;
import com.gang.cloud.dubbo.to.UserTO;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2020/9/30 17:10
 * @Created by zengzg
 */
@Service(version = "${dubbo.service.version}")
public class UserService implements IUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserTO getUser() {
        logger.info("------> this is in get user <-------");
        return new UserTO("gang");
    }

    @Override
    public UserTO fuse() {
        logger.info("------> fuse <-------");
        return null;
    }
}
