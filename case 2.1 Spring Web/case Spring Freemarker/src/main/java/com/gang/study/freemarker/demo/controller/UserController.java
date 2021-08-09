package com.gang.study.freemarker.demo.controller;

import com.gang.study.freemarker.demo.to.UserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2021/1/9 17:44
 * @Created by zengzg
 */
@RequestMapping(value = "/user")
@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ModelAndView getUser() {
        logger.info("------> this in in user <-------");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");

        UserTO userTO = new UserTO();
        userTO.setUsername("gang");
        modelAndView.addObject("user", userTO);

        return modelAndView;
    }
}
