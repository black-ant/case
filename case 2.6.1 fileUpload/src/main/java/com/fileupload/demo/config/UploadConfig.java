package com.fileupload.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @Classname UploadConfig
 * @Description TODO
 * @Date 2021/1/10 13:57
 * @Created by zengzg
 */
@Configuration
public class UploadConfig {

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(10485760);
        resolver.setMaxInMemorySize(40960);
        return resolver;
    }

}
