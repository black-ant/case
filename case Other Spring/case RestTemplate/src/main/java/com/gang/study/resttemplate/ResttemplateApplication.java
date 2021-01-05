package com.gang.study.resttemplate;

import com.gang.study.resttemplate.service.StartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResttemplateApplication {

    @Autowired
    private StartService startService;

    public static void main(String[] args) {
        SpringApplication.run(ResttemplateApplication.class, args);
    }

}
