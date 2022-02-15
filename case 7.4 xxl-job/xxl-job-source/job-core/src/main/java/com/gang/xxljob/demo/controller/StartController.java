package com.gang.xxljob.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StartController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @GetMapping("start")
    public void Start(){
        logger.info("------> start in application <-------");
    }
}
