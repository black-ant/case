package com.gang.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname WebflowDemoApplication
 * @Description TODO
 * @Date 2021/6/6
 * @Created by zengzg
 */
@SpringBootApplication
public class WebflowDemoApplication {

    /**
     * activation-flow.xml 访问地址 : http://127.0.0.1:8080/activationFlow
     * select-flow.xml 访问地址 : http://127.0.0.1:8080/selectFlow
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(WebflowDemoApplication.class, args);
    }

}
