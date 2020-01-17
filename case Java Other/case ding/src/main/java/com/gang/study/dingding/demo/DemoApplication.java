package com.gang.study.dingding.demo;

import com.gang.study.dingding.demo.service.DingStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private DingStartService dingStartService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
