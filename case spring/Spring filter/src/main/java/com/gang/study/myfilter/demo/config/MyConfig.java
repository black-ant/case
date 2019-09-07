package com.gang.study.myfilter.demo.config;

import com.gang.study.myfilter.demo.filter.MyFilter;
import com.gang.study.myfilter.demo.listener.MyListener;
import com.gang.study.myfilter.demo.servlet.MyServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {


    @Bean
    public ServletRegistrationBean MyServlet() {
        return new ServletRegistrationBean(new MyServlet(), "/*");
    }

    @Bean
    public FilterRegistrationBean getFilter() {
        return new FilterRegistrationBean(new MyFilter());
    }

    
//    @Bean
//    public ServletListenerRegistrationBean<MyListener> getListener() {
//        return new ServletListenerRegistrationBean<>(new MyListener());
//
//    }
}