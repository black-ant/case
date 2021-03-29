package com.gang.cloud.dubbo.controller;

import com.gang.cloud.service.IUserService;
import com.gang.cloud.to.User;
import org.apache.dubbo.config.annotation.Reference;
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
@RequestMapping("user")
public class UserController {

    @Reference(lazy = true, check = false)
    IUserService userService;

    /**
     * 功能描述: 调用用户登陆的处理
     *
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/{username}", method = RequestMethod.GET)
    public String login(@PathVariable String username) {
        User user = userService.getUser();
        return user.getUsername();
    }
}
