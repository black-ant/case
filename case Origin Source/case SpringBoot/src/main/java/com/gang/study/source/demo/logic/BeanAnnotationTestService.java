package com.gang.study.source.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname BeanAnnotationTestService
 * @Description TODO
 * @Date 2021/2/18 14:22
 * @Created by zengzg
 */
public class BeanAnnotationTestService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String name;

    public String showInfo() {
        logger.info("------> this is showInfo <-------");
        return "showInfo";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
