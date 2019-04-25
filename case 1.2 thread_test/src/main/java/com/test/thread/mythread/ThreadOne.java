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
        Long time = System.currentTimeMillis();
        logger.info("线程 {} 开始运行，唯一ID ：{} ,开始时间：{}", Thread.currentThread().getName(),this.getId(),time);
        logger.info("---------------------");
        try {
            this.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("run  线程 {} 是否存活：status:{}", Thread.currentThread().getName(), this.isAlive());
//        logger.info("当前线程信息:{}", JSONObject.toJSONString(Thread.currentThread()));
        logger.info("线程 -{} 运行结束,运行时间：{}", Thread.currentThread().getName(),System.currentTimeMillis() - time);
    }
}
