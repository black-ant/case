package com.gang.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname WebflowDemoApplication
 * @Description
 * @Date 2021/6/6
 * @Created by zengzg
 */
@SpringBootApplication
public class WebflowDemoApplication {

    /**
     * 访问地址 : http://127.0.0.1:8080/activationFlow
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(WebflowDemoApplication.class, args);
    }

}
