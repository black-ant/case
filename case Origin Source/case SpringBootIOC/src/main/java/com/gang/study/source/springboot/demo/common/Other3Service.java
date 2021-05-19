package com.gang.study.source.springboot.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Classname OtherService
 * @Description TODO
 * @Date 2021/4/14
 * @Created by zengzg
 */
@Component
public class Other3Service {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 测试方法
     *
     * @return
     */
    public String showInfo() {
        logger.info("------> this is OtherService showInfo <-------");
        return "success";
    }


}
