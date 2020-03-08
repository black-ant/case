package com.gang.antsso.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/2/23 17:29
 * @Created by zengzg
 */
@RestController
@RequestMapping("/index")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("one")
    public String getIndex() {
        logger.info("------> this is in inde one <-------");
        return "ok";
    }
}
