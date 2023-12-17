package com.gang.bean.demo.config;

import com.gang.bean.demo.config.service.ConfigBeanB;
import com.gang.bean.demo.config.service.ConfigBeanC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @Classname ConfigutationC
 * @Description TODO
 * @Date 2021/10/21
 * @Created by zengzg
 */
//@Configuration
//@Order(0)
public class ConfigutationC {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ConfigutationC() {
        logger.info("------> this is  ConfigutationC<-------");
    }

    @Bean(name = "configBeanC")
    public ConfigBeanC getConfigBeanC() {
//        System.out.println("ConfigBeanC 加载了");
        return new ConfigBeanC();
    }
}
