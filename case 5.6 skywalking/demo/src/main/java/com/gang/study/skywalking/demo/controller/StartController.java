package com.gang.study.skywalking.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/11/20 21:32
 * @Created by zengzg
 */
@RestController
@RequestMapping("test")
public class StartController {


    @GetMapping
    public String get() {
        return "success";
    }
}
