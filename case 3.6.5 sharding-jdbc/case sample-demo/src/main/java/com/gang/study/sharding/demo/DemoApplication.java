package com.gang.study.sharding.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 参考网址 @ https://www.jianshu.com/p/f4aea81173f6
 */
@SpringBootApplication
@MapperScan("com.gang.study.sharding.demo.dao")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
