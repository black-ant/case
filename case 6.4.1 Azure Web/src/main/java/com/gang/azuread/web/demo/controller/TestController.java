package com.gang.azuread.web.demo.controller;

import com.gang.azuread.web.demo.logic.TokenLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/3/30 21:39
 * @Created by zengzg
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenLogic tokenLogic;

    @GetMapping("/one")
    public String one() {
        logger.info("------> this is in one <-------");
        return "ok";
    }

    @GetMapping("/token")
    public String token() {
        //        tokenLogic.
        return null;
    }
}
