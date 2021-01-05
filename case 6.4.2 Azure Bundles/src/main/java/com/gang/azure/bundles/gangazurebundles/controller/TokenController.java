package com.gang.azure.bundles.gangazurebundles.controller;

import com.gang.azure.bundles.gangazurebundles.entity.AzureClientTokenTO;
import com.gang.azure.bundles.gangazurebundles.logic.TokenLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TokenController
 * @Description TODO
 * @Date 2020/3/31 20:11
 * @Created by zengzg
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenLogic tokenLogic;

    @GetMapping("get")
    public String get() {
        logger.info("------> this is token <-------");
        String token;
        token = tokenLogic.getToken(new AzureClientTokenTO());
        return token;
    }

    @GetMapping("test")
    public String test() {
        logger.info("------> this is test <-------");
        return "test";
    }
}
