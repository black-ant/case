package com.gang.sample.webflow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * @Classname WebMvcConfig
 * @Description TODO
 * @Date 2021/6/6
 * @Created by zengzg
 */
@Configuration
public class WebMvcConfig {

    @Bean
    public ViewResolver getViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        return thymeleafViewResolver;
    }
}
