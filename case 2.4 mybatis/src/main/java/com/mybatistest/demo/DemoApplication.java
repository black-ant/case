package com.mybatistest.demo;

import com.mybatistest.demo.service.StartService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.mybatistest.demo.mapper")
public class DemoApplication {

    @Autowired
    StartService startService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
