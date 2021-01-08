package com.gang.cloud.dubbo;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname ServerAppliccation
 * @Description TODO
 * @Date 2020/9/29 23:20
 * @Created by zengzg
 */

@EnableDubboConfiguration
@SpringBootApplication
public class ServerAppliccation {


    public static void main(String[] args) {
        SpringApplication.run(ServerAppliccation.class, args);
    }
}
