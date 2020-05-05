package com.gang.study.oauth.dingtalk.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Classname MyMVCConfig
 * @Description TODO
 * @Date 2020/5/1 20:27
 * @Created by zengzg
 */
@Configuration
public class MyMVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/test").setViewName("/index.html");
    }
}
