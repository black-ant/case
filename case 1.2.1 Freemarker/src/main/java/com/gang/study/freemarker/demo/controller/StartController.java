package com.gang.study.freemarker.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/1/20 13:54
 * @Created by zengzg
 */
@RequestMapping(value = "/start")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/login")
    public String loginFtl() {
        logger.info("------> this in in login <-------");
        return "/templates/login";
    }

}
