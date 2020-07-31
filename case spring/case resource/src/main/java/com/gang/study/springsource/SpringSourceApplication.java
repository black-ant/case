package com.gang.study.springsource;


import com.gang.study.springsource.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSourceApplication {

    @Autowired
    TestService testService;

    public static void main(String[] args) {
        SpringApplication.run(SpringSourceApplication.class, args);
    }
}
