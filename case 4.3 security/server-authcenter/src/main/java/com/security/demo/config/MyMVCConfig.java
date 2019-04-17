package com.security.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/16 15:01
 * @Version 1.0
 **/
@Configuration
public class MyMVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login").setViewName("login");
    }
}
