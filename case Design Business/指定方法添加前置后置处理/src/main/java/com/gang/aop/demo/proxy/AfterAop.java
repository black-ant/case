package com.gang.aop.demo.proxy;

import com.gang.aop.demo.annotation.ProxyParam;
import com.gang.aop.demo.entity.LogicEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname AfterLogic
 * @Description TODO
 * @Date 2020/10/28 21:55
 * @Created by zengzg
 */
public class AfterAop implements IAop {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(@ProxyParam(paramName = "entity") LogicEntity entity) {
        logger.info("------> do AfterAop <-------");
    }
}
