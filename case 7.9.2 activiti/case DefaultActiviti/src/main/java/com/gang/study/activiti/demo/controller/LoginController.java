package com.gang.study.activiti.demo.controller;

import com.gang.study.activiti.demo.entity.UserEntity;
import com.gang.study.activiti.demo.logic.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Created by yawn on 2018-01-08 13:39
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;

    @PostMapping("/doLogin")
    @ResponseBody
    public boolean login(HttpSession session, @RequestBody UserEntity userEntity) {
        logger.info("------> 通过 login 接口完成登录 <-------");
        String userName = userEntity.getUserName();
        String password = userEntity.getPassword();
        boolean login = userService.login(userName, password);
        if (login) {
            session.setAttribute("userName", userName);
            return true;
        }
        return false;
    }

    public String exit(HttpSession session) {
        session.removeAttribute("userName");
        return "login";
    }
}
