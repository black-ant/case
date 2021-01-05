package com.gang.study.annotation.demo;

import com.gang.study.annotation.demo.service.StartTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    StartTest startTest;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
