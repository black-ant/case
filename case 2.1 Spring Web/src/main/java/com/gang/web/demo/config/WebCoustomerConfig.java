package com.gang.web.demo.config;

import com.gang.web.demo.interceptor.ConfigInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Classname WebSecurityConfig
 * @Description TODO
 * @Date 2021/1/12 18:29
 * @Created by zengzg
 */
@Configuration
public class WebCoustomerConfig implements WebMvcConfigurer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("------> this is addInterceptors  <-------");
        registry.addInterceptor(new ConfigInterceptor());
    }


}

