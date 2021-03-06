package com.gang.cloud.dubbo.provide.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.gang.cloud.dubbo.service.IUserService;
import com.gang.cloud.dubbo.to.UserTO;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2020/9/30 17:10
 * @Created by zengzg
 */
@Service(version = "${dubbo.service.version}")
public class UserService implements IUserService {

    @Override
    public UserTO getUser() {
        return new UserTO("gang");
    }
}
