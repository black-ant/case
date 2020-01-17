package com.gang.study.taskkafka.demo;

import com.gang.study.taskkafka.demo.service.StartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private StartService startService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
