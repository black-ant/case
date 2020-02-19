package com.gang.adbundles.demo.controller;

import com.gang.adbundles.demo.service.ADService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/25 18:43
 * @Version 1.0
 **/
@RestController
public class AccountController {

    @Autowired
    ADService adService;

//    @GetMapping("isalive")
//    public void testADConnection(){
//        adService.isAlive();
//    }

    @GetMapping
    public void setConfiguration(){

    }


}
