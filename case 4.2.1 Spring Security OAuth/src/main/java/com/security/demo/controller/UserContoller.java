package com.security.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname UserContoller
 * @Description TODO
 * @Date 2021/4/12
 * @Created by zengzg
 */
@RestController
@RequestMapping("user")
public class UserContoller {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("get")
    public String getUserInfo() {

        logger.info("------> this is in getUserinfo <-------");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "None";
        if (authentication != null) {
            username = authentication.getName();
        }
        return "success : " + username;
    }
}
