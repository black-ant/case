//package com.gang.bean.demo.config;
//
//import com.gang.bean.demo.config.service.ConfigBeanA;
//import com.gang.bean.demo.config.service.ConfigBeanB;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.core.annotation.Order;
//
///**
// * @Classname ConfigurationOrder
// * @Description TODO
// * @Date 2023/12/16
// * @Created by zengzg
// */
//@Slf4j
//@Configuration
//@Order(7)
//public class ConfigurationOrder {
//
//    @Bean
//    @Order(10)
//    public ConfigBeanB getConfigBeanB() {
//        log.info("------> ConfigurationOrder ConfigBeanB  <-------");
//        return new ConfigBeanB();
//    }
//
//    @Bean
//    @Order(1)
//    public ConfigBeanA getConfigBeanA() {
//        log.info("------> ConfigurationOrder ConfigBeanA  <-------");
//        return new ConfigBeanA();
//    }
//
//
//}
