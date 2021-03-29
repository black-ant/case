package com.gang.cloud.dubbo.server;

import com.alibaba.dubbo.config.annotation.Service;
import com.gang.cloud.service.IUserService;
import com.gang.cloud.to.User;
import org.springframework.stereotype.Component;

/**
 * @Classname UserServiceImpl
 * @Description TODO
 * @Date 2020/9/29 22:38
 * @Created by zengzg
 */
@Component
@Service
public class UserServiceImpl implements IUserService {

    @Override
    public User getUser() {
        return new User("gang");
    }
}
