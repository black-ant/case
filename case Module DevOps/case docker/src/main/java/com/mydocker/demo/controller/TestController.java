package com.mydocker.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/4 20:14
 * @Version 1.0
 **/
@RestController
public class TestController {

    @GetMapping("get")
    public String getInfo(){
        return "get is success";
    }
}
