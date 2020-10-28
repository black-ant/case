package com.gang.aop.demo.service;

import com.gang.aop.demo.entity.LogicEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartLogin
 * @Description TODO
 * @Date 2020/10/28 17:12
 * @Created by zengzg
 */
@Component
public class StartLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AopLogic aopLogic;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        LogicEntity entity = new LogicEntity("gang", "username");
//        entity = aopLogic.doLogic(entity, "info");
//        logger.info("------> this is entity :{} <-------", entity);
    }
}
