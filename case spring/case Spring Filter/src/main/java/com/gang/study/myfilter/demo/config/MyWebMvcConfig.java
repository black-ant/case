package com.gang.study.myfilter.demo.config;

import com.gang.study.myfilter.demo.interceptor.MyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("------> addInterceptors <-------");
        registry.addInterceptor(new MyInterceptor());
    }
}
