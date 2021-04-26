package com.gang.study.source.springboot.demo.circular;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @Classname BeanCService
 * @Description TODO
 * @Date 2021/4/20
 * @Created by zengzg
 */
@Component
//@Scope(value = "prototype")
public class BeanCService {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BeanAService beanAService;

    public BeanCService() {
        logger.info("------> this is BeanCService Init <-------");
    }

    public void getInfo() {
        logger.info("------> this is BeanCService <-------");
        beanAService.getInfo2();
    }
}
