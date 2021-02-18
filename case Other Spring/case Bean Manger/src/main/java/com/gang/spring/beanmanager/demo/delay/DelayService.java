package com.gang.spring.beanmanager.demo.delay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname DelayService
 * @Description TODO
 * @Date 2021/2/18 9:42
 * @Created by zengzg
 */
public class DelayService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String name = "antBlack";

    public DelayService(String name) {
        this.name = name;
    }

    public void showInfo() {
        logger.info("------> this is in show info <-------");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
