package com.gang.aop.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.target.AbstractBeanFactoryBasedTargetSource;

/**
 * @Classname CustomTargetSource
 * @Description TODO
 * @Date 2021/6/28
 * @Created by zengzg
 */
public class DefaultCustomTargetSource extends AbstractBeanFactoryBasedTargetSource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object getTarget() throws Exception {
        logger.info("------> 进入 [DefaultCustomTargetSource] , 返回当前目标对象 <-------");
        return getBeanFactory().getBean(getTargetBeanName());
    }
}
