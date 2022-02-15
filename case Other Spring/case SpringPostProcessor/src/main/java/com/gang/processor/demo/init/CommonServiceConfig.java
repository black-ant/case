package com.gang.processor.demo.init;

import com.gang.processor.demo.service.CustomizePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname CommonServiceConfig
 * @Description TODO
 * @Date 2021/9/25
 * @Created by zengzg
 */
@Configuration
public class CommonServiceConfig {


    @Autowired
    private DefaultListableBeanFactory beanFactory;

    @Bean(initMethod = "initMethod")
    public CommonService getCommonService() {

        beanFactory.addBeanPostProcessor(new CustomizePostProcessor());

        return new CommonService();
    }
}
