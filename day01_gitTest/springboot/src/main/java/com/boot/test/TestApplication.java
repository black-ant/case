package com.boot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/index")
public class TestApplication {

    @RequestMapping("/test")
    public void test(){
        System.out.println("success");
    }

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
