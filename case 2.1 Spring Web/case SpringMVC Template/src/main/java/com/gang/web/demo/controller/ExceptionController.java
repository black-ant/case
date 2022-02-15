package com.gang.web.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ExceptionController
 * @Description TODO
 * @Date 2021/11/3
 * @Created by zengzg
 */
@RestController
@RequestMapping("/ex")
public class ExceptionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String doEX() {
        logger.info("------> this is do ex <-------");
        throw new NullPointerException();
    }

}
