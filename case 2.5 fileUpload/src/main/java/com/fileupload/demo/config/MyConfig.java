package com.fileupload.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc // 1开启默认配置
@EnableScheduling
@ComponentScan("com.fileupload.demo")
public class MyConfig implements WebMvcConfigurer {//2


    //viewController
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/index").setViewName("/index");
        registry.addViewController("/toUplad").setViewName("/upload");
    }
}

