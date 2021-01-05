package com.gang.study.springtogether.user.demo;

import com.gang.study.springtogether.user.demo.service.StartTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.gang"})
public class DemoApplication {

    @Autowired
    private StartTwoService startService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
