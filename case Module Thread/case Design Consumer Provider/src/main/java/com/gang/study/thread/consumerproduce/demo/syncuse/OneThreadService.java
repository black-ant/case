package com.gang.study.thread.consumerproduce.demo.syncuse;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Classname TestServicec
 * @Description TODO
 * @Date 2022/3/9
 * @Created by zengzg
 */
@Service
public class OneThreadService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 重试队列 : 控制容量为300
     */
    private static ArrayBlockingQueue<BasePageBean> arrayBlockingQueue = new ArrayBlockingQueue<BasePageBean>(300);
    private static Object lock = new Object();

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

    public void test() {
        Consumer consumer = new Consumer();
        consumer.start();
        synchronized (lock) {
            for (int i = 0; i < 200; i++) {

                BasePageBean request = new BasePageBean();
                request.setTargets("123-" + i);
                try {
                    logger.info("插入一个数据");
                    arrayBlockingQueue.put(request);
                    this.lock.notify();
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            logger.info("全部执行完成");

            while (true) {
                if (!arrayBlockingQueue.isEmpty()) {
                    try {
                        this.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



    class Consumer extends Thread {

        @Override
        public void run() {
            retry();
        }

        public void retry() {

            synchronized (lock) {
                while (true) {
                    try {
                        if (arrayBlockingQueue.isEmpty()) {
                            logger.info("数据为空 ，锁定对象");
                            lock.wait();
                        } else {
                            BasePageBean messageSendRequest = arrayBlockingQueue.poll(10, TimeUnit.SECONDS);
                            logger.info("获取一个对象：:{}", JSONObject.toJSONString(messageSendRequest));
                        }
                    } catch (Exception e) {
                        logger.error("弹出数据失败:{},Message:{}", e.getClass(), e.getMessage());
                    }
                    logger.info("当前循环执行完成");
                }
            }
        }
    }
}
