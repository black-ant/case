package com.myshiro.shirooauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/23 23:04
 * @Version 1.0
 **/
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("index").setViewName("index");
        registry.addViewController("login").setViewName("login");
    }
}
