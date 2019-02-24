package com.myshiro.shirooauth.controller;

import com.myshiro.shirooauth.common.Wrapper;
import com.myshiro.shirooauth.common.WrapperResponse;
import com.myshiro.shirooauth.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/22 22:46
 * @Version 1.0
 **/
@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private LoginService loginService;

    @RequestMapping(value = "/loginfun", method = RequestMethod.POST)
    public String login(String username, String password) {
        logger.info("登陆过程：{}--{}", username, password);
        loginService.login(username,password);
        return "main/main.html";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Wrapper index() {
        return WrapperResponse.success("登陆成功!");
    }

    @RequestMapping(value = "/notLogin", method = RequestMethod.GET)
    public Wrapper notLogin() {
        return WrapperResponse.success("您尚未登陆!");
    }

    @RequestMapping(value = "/notRole", method = RequestMethod.GET)
    public Wrapper notRole() {
        return WrapperResponse.success("您没有权限!");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout() {
        logger.info("退出过程开始");
        loginService.logout();
        return "index";
    }
}
