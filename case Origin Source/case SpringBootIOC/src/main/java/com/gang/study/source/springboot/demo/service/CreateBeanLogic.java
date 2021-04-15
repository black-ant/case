package com.gang.study.source.springboot.demo.service;

import com.gang.study.source.springboot.demo.common.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname CreateBeanLogic
 * @Description Bean 加载流程测试
 * @Date 2021/3/2 15:23
 * @Created by zengzg
 */
@Service
public class CreateBeanLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonService commonService;

    /**
     * 测试方法
     * C- AbstractAutowireCapableBeanFactory
     *
     * @return
     */
    public String showInfo() {
        logger.info("------> this is showInfo <-------");
        logger.info("------> 第一步 :  <-------");
        return "success";
    }

}
