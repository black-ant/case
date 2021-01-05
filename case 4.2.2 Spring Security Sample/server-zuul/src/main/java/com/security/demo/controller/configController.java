package com.security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/1/21 21:16
 * @Version 1.0
 **/
@RestController
@RefreshScope
public class configController {

//    @Value("${paramone}")
    String configname;

    @Autowired
    Environment env;

    @RequestMapping("/getconfigname")
    public String getconfigname() {
        return this.configname;
    }

    @RequestMapping("/sang2")
    public String sang2() {
        return env.getProperty("config-name", "未定义");
    }
}
