package com.gang.cloud.template.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ConfigController
 * @Description TODO
 * @Created by zengzg
 */

@RestController
@RequestMapping("/config")
public class ConfigTestController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;


    @Value("${gang.test.name:zhang}")
    private String name;

    @Value("${gang.test.age:18}")
    private Integer age;

    @Value("${gang.test.add:湖南}")
    private String address;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }

    @GetMapping("/user")
    public String gerUserInfo() {
        return "name:" + name + "--age:" + age + "---address:{}" + address;
    }
}
