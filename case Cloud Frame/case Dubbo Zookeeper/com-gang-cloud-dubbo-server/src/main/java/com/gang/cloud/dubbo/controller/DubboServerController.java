package com.gang.cloud.dubbo.controller;

import com.gang.cloud.service.IAccountService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname DubboServerController
 * @Description TODO
 * @Date 2020/8/6 22:53
 * @Created by zengzg
 */
@RequestMapping("server")
@RestController
public class DubboServerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference(lazy = true, check = false)
    IAccountService accountService;

    @GetMapping("get")
    public String doClient() {
        return accountService.serverAskClient();
    }
}
