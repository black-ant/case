package com.gang.study.io.demo;

import com.gang.study.io.demo.service.StartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    StartService startService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
