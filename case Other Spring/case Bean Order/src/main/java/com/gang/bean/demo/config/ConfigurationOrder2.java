//package com.gang.bean.demo.config;
//
//import com.gang.bean.demo.config.service.ConfigBeanB;
//import com.gang.bean.demo.config.service.ConfigBeanC;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
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
//@Order(20)
//public class ConfigurationOrder2 {
//
//    @Bean
//    @Order(7)
//    public ConfigBeanC getConfigBeanC() {
//        log.info("------> ConfigurationOrder ConfigBeanC  <-------");
//        return new ConfigBeanC();
//    }
//
//}
