package com.myshiro.shirooauth.controller;

import com.myshiro.shirooauth.common.Wrapper;
import com.myshiro.shirooauth.common.WrapperResponse;
import com.myshiro.shirooauth.service.LoginService;
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

    @Resource
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Wrapper login(String name, String password){
        return WrapperResponse.success(loginService.login(name,password));
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Wrapper index(){
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

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Wrapper logout() {
        return WrapperResponse.wrap(200, "操作成功", loginService.logout());
    }
}
