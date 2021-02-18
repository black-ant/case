package com.gang.study.source.demo.configuration;

import com.gang.study.source.demo.logic.BeanAnnotationTestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname BeanAnnotationConfig
 * @Description TODO
 * @Date 2021/2/18 14:23
 * @Created by zengzg
 */
@Configuration
public class BeanAnnotationConfig {

    @Bean
    public BeanAnnotationTestService getBeanAnnotationTestService() {
        return new BeanAnnotationTestService();
    }
}
