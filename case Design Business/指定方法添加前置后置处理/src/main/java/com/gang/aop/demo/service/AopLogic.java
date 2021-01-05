package com.gang.aop.demo.service;

import com.gang.aop.demo.entity.LogicEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname Logic
 * @Description TODO
 * @Date 2020/10/28 16:55
 * @Created by zengzg
 */
@Component
public class AopLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * doLogic
     *
     * @return
     */
    public LogicEntity doLogic(LogicEntity entity, String info) {
        logger.info("------> this is entity :{} <-------", entity);
        entity.setType(info);
        return entity;
    }
}
