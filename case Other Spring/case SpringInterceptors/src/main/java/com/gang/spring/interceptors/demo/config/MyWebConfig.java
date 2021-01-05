package com.gang.spring.interceptors.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Classname MyWebConfig
 * @Description TODO
 * @Date 2020/5/27 19:12
 * @Created by zengzg
 */
@Configuration
public class MyWebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个，这里选择拦截所有请求地址，进入后判断是否有加注解即可
        //        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/test/**");
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/test/levelTwo/**");
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/test/levelTwo/one/1");


        // 判断拦截器是否能对所有请求拦截 , 及拦截的时机
        registry.addInterceptor(new ActionInterceptor()).addPathPatterns("/**");
    }
}
