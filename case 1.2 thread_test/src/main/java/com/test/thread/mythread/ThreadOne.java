package com.test.thread.mythread;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/22 23:17
 * @Version 1.0
 **/
public class ThreadOne extends Thread {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        logger.info("thread is run ,time is :{}", Thread.currentThread().getName());
        logger.info("---------------------");
        logger.info("当前线程信息:{}", JSONObject.toJSONString(Thread.currentThread()));
    }
}
