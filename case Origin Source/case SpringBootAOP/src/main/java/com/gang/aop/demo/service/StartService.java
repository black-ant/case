package com.gang.aop.demo.service;

import com.gang.aop.demo.assignable.ChildService;
import com.gang.aop.demo.assignable.ParentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/6/21
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> [测试 Application 启动时调用开始] <-------");
        get();
        logger.info("------> [测试 Application 启动时调用结束] <-------");

        logger.info("------> 测试 isAssignable <-------");
        logger.info("------> ChildService By ParentService :{} <-------", ClassUtils.isAssignable(ChildService.class, ParentService.class));
        logger.info("------> ParentService By ChildService:{} <-------", ClassUtils.isAssignable(ParentService.class, ChildService.class));
    }

    public String get() {
        logger.info("------> this is in StartService <-------");
        return "success";
    }


}
