package com.gang.study.module.gangstudymodule;

import com.gang.study.module.gangstudymodule.service.StartService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.gang"})
@MapperScan("com.gang")
public class GangStudyModuleApplication {

    @Autowired
    StartService startService;

    public static void main(String[] args) {
        SpringApplication.run(GangStudyModuleApplication.class, args);
    }

}
