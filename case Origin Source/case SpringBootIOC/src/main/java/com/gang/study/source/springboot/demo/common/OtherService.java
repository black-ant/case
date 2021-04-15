package com.gang.study.source.springboot.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname OtherService
 * @Description TODO
 * @Date 2021/4/14
 * @Created by zengzg
 */
public class OtherService {

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
