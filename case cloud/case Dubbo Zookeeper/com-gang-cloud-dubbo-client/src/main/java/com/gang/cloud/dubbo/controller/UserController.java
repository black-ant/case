package com.gang.cloud.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gang.cloud.service.IUserService;
import com.gang.cloud.to.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2020/9/29 23:29
 * @Created by zengzg
 */

@RestController
public class UserController {

    @Reference(version = "${service.version}", check = false)
    IUserService userService;

    /**
     * 代码创建: yellowcong <br/>
     * 创建日期: 2019年3月30日 <br/>
     * 功能描述: 调用用户登陆的处理
     *
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String login(@PathVariable String username) {
        User user = userService.getUser();
        return user.getUsername();
    }
}
