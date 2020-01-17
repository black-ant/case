package com.security.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usermanage")
public class UserManageContoller {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("test001")
    public String test001() {
        return "home";
    }


}
