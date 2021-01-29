package com.gang.oauth.pkce.demo.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname LoginController
 * @Description TODO
 * @Date 2021/1/29 22:16
 * @Created by zengzg
 */
@RestController
@RequestMapping("/doLogin")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public void doLogin(){

    }



}
