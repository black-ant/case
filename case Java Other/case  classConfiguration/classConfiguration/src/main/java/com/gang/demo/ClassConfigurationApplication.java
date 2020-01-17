package com.gang.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ClassConfigurationApplication {

    @Value("${gang.name}")
    private String name;
    @Value("${gang.url}")
    private String url;

    @RequestMapping("/index")
    public String index(){
        return  "welcome "+name+"springBoot"+":"+url;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClassConfigurationApplication.class, args);
    }
}
