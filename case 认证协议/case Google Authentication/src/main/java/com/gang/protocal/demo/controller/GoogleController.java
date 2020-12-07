package com.gang.protocal.demo.controller;

import com.gang.protocal.demo.service.StartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * @Classname GoogleController
 * @Description TODO
 * @Date 2020/12/1 9:43
 * @Created by zengzg
 */
@RestController
@RequestMapping("/google")
public class GoogleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StartService startService;

    @GetMapping("/test/test")
    public String getLocale() {
        logger.info("------> Locale.CHINA :{} <-------", Locale.CHINA);
        return "success";
    }

    @GetMapping("/{code}")
    public Boolean checkCode(@PathVariable("code") String code) {
        logger.info("------> this is check :{}<-------", code);
        return startService.checkCode(code);
    }
}
