package com.gang.cloud.dubbo.service;

import com.gang.cloud.dubbo.to.UserTO;

import java.io.Serializable;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2020/9/30 16:59
 * @Created by zengzg
 */
public interface IUserService {

    /**
     * dubbo 测试
     *
     * @return
     */
    UserTO getUser();

    /**
     * Dubbo 熔断测试
     *
     * @return
     */
    UserTO fuse();


}
