package com.gang.aop.demo.proxy;

import com.gang.aop.demo.entity.LogicEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname ExImpl
 * @Description TODO
 * @Date 2020/10/28 17:23
 * @Created by zengzg
 */
public class MySelfService implements ExInterface {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(LogicEntity entity) {
        logger.info("------> do  MySelfService<-------");
    }
}

