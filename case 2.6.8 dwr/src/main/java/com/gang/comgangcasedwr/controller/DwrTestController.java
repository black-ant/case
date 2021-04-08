package com.gang.comgangcasedwr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname DwrTestController
 * @Description TODO
 * @Date 2021/4/8
 * @Created by zengzg
 */
@Controller
@RequestMapping("demo1")
public class DwrTestController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
