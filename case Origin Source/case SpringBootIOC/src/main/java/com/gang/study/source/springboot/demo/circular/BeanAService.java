package com.gang.study.source.springboot.demo.circular;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @Classname BeanAService
 * @Description TODO
 * @Date 2021/4/20
 * @Created by zengzg
 */
@Component
//@Scope(value = "prototype")
public class BeanAService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BeanBService beanBService;

    public BeanAService() {
        logger.info("------> this is BeanAService Init <-------");
    }


    public void getInfo() {
        logger.info("------> this is BeanAService <-------");
        beanBService.getInfo();
    }

    public void getInfo2() {
        logger.info("------> this is BeanAService <-------");
    }

}
