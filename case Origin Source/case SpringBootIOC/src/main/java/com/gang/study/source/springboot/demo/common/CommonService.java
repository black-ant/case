package com.gang.study.source.springboot.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Classname CommonService
 * @Description TODO
 * @Date 2021/3/2 15:24
 * @Created by zengzg
 */
//@Service
public class CommonService implements InitializingBean, ApplicationRunner, BeanNameAware{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String beanName;

    @Autowired
    private Other3Service other3Service;

    public void initMethod() {
        logger.info("------> this is in @Bean(initMethod = \"initMethod\")  <-------");
        other3Service.showInfo();
    }

    @PostConstruct
    public void init() {
        logger.info("------> this is @PostConstruct <-------");
        other3Service.showInfo();
    }

    @Override
    public void afterPropertiesSet() {
        logger.info("------> this is InitializingBean  <-------");
        other3Service.showInfo();
    }


    /**
     * 测试方法
     *
     * @return
     */
    public String showInfo() {
        logger.info("------> this is showInfo <-------");
        return "success";
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is ApplicationRunner :{} <-------", beanName);
        other3Service.showInfo();
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }


//    @Bean
//    public Other2Service getOtherService() {
//        return new Other2Service();
//    }

}
