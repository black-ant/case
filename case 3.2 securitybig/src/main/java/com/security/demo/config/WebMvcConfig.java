package com.security.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("home");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/logout").setViewName("/index");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/admin2").setViewName("user");
    }
}
