package com.gang.study.activiti.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 参考官方方案及补充 : https://spring.io/blog/2015/03/08/getting-started-with-activiti-and-spring-boot
 */
@SpringBootApplication(exclude = {
        org.activiti.spring.boot.SecurityAutoConfiguration.class
})
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }


    /**
     * 启动教程 :
     *
     * Step 1 访问首页 http://localhost:8087/index
     * Step 2
     */

}
