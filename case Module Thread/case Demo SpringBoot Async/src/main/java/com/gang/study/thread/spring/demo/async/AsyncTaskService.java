package com.gang.study.thread.spring.demo.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Classname TaskLogic
 * @Description TODO
 * @Date 2020/4/17 14:10
 * @Created by zengzg
 */
@Component
public class AsyncTaskService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    //表明该方法是异步方法，如果注解在类上，则表明该类的所有方法都是异步方法，
    //这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor
    @Async
    public void executeAsyncTask(Integer i) {
        logger.info("=== ------> this is one : {} <-------", i);
    }

    @Async
    public void executeAsyncTaskPlus(Integer i) {
        logger.info("+++ ------> this is one : {} <-------", i);
    }
}
