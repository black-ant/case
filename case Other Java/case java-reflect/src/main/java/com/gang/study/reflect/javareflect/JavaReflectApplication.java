package com.gang.study.reflect.javareflect;

import com.gang.study.reflect.javareflect.service.StratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaReflectApplication {

    @Autowired
    StratService stratService;

    public static void main(String[] args) {
        SpringApplication.run(JavaReflectApplication.class, args);
    }

}
