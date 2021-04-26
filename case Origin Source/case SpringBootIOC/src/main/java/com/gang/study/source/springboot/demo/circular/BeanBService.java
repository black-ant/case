package com.gang.study.source.springboot.demo.circular;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @Classname BeanBService
 * @Description TODO
 * @Date 2021/4/20
 * @Created by zengzg
 */
@Component
//@Scope(value = "prototype")
public class BeanBService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BeanCService beanCService;

    public BeanBService() {
        logger.info("------> this is BeanBService Init <-------");
    }


    public void getInfo() {
        logger.info("------> this is BeanBService <-------");
        beanCService.getInfo();
    }
}
