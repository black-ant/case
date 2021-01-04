package com.gang.study.jerseyspringboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * Jersey 的注册方式有三种 :
 * 1 application 中直接添加
 */
@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

    //    public static void main(String[] args) {
    //        SpringApplication.run(DemoApplication.class, args);
    //    }


    /**
     * 方式一
     * https://www.jianshu.com/p/c14a9028e6e7
     * @param args
     */
    public static void main(String[] args) {
        new DemoApplication()
                .configure(new SpringApplicationBuilder(DemoApplication.class))
                .run(args);
    }

}
