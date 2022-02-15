package com.gang.processor.demo.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname TypeBeanStarter
 * @Description TODO
 * @Date 2021/10/7
 * @Created by zengzg
 */
@Service
public class TypeBeanStartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BeanManager beanManager;

    @Autowired
    private TypeBeanImpl typeBean;

    @Autowired
    private TypeBeanImpl typeBean2;

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> BeanManager 管理 <-------");

        Map<String, TypeBean> typeOne = beanManager.getTypeOne();
        Map<String, TypeBean> typeTwo = beanManager.getTypeTwo();

        logger.info("------> BeanManger :{} <-------", typeOne.size());
        logger.info("------> BeanManger :{} <-------", typeTwo.size());
        logger.info("------> BeanManger :{} <-------", typeTwo.size());
        logger.info("------> typeOne.get(\"typeBeanImpl\") == (typeBean) :{} <-------", typeOne.get("typeBeanImpl") == (typeBean));
        logger.info("------> {} <-------", typeBean == typeBean2);


    }
}
