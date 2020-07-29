package com.gang.study.miaosha.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/7/24 17:36
 * @Created by zengzg
 */
@RequestMapping("/thread")
@RestController
@RequestScope
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("excute")
    public void excuteGet() {
        logger.info("------> this is in excuteGet <-------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("excute")
    public void excutePost() {
        logger.info("------> this is in excutePost <-------");
    }
}
