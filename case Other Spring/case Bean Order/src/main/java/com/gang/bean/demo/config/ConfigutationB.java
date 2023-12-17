package com.gang.bean.demo.config;

import com.gang.bean.demo.config.service.ConfigBeanA;
import com.gang.bean.demo.config.service.ConfigBeanB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @Classname ConfigutationB
 * @Description TODO
 * @Date 2021/10/21
 * @Created by zengzg
 */
//@Configuration
//@Order(1)
public class ConfigutationB {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ConfigutationB() {
        logger.info("------> this is  ConfigutationB<-------");
    }

    @Bean(name = "configBeanB")
    public ConfigBeanB getConfigBeanB() {
//        System.out.println("ConfigBeanB 加载了");
        return new ConfigBeanB();
    }
}
