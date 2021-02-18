package com.gang.spring.beanmanager.demo.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Classname DynamicService
 * @Description TODO
 * @Date 2021/2/18 10:05
 * @Created by zengzg
 */
@Service
public class DynamicService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String name = "antBlack";

    public DynamicService() {
    }

    public DynamicService(String name) {
        this.name = name;
    }

    public void show() {
        logger.info("------> DynamicService showName :{}  <-------", name);
    }


}
