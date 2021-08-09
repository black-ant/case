package com.mythymeleaf.demo.controller;

import com.mythymeleaf.demo.to.ContentTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Classname LoginControlller
 * @Description TODO
 * @Date 2021/1/10 18:25
 * @Created by zengzg
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "/doLogin")
    public ModelAndView doLogin(String username, String password) {
        logger.info("------> this in in login :{} <-------", username);
        ModelAndView modelAndView = new ModelAndView();
        if ("ant".equals(username) && "123456".equals(password)) {
            modelAndView.setViewName("redirect:/user");
        } else {
            modelAndView.setViewName("login");
            modelAndView.addObject("errorMsg", "账号密码错误");
        }
        return modelAndView;
    }
}
