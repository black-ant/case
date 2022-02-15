package com.gang.bean.demo.config;

import com.gang.bean.demo.config.service.ConfigBeanA;
import com.gang.bean.demo.config.service.ConfigBeanC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

/**
 * @Classname ConfigutationA
 * @Description TODO
 * @Date 2021/10/21
 * @Created by zengzg
 */
@Configuration
//@Order(3)
public class ConfigutationA {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ConfigutationA() {
        logger.info("------> this is  ConfigurationA<-------");
    }

    @Bean
    @DependsOn("configBeanC")
    public ConfigBeanA getConfigBeanA() {
        System.out.println("ConfigBeanA 加载了");
        return new ConfigBeanA();
    }

}
