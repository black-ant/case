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
 * @Classname SleepThreadService
 * @Description TODO
 * @Date 2022/3/13
 * @Created by zengzg
 */
@Service
public class SleepThreadService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 重试队列 : 控制容量为300
     */
    private static ArrayBlockingQueue<BasePageBean> arrayBlockingQueue = new ArrayBlockingQueue<BasePageBean>(300);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> 线程处理开始 <-------");
//        Producer producer = new Producer();
//        Producer producer1 = new Producer();
//        Consumer consumer = new Consumer();
//        Consumer consumer1 = new Consumer();
//        consumer.start();
//        consumer1.start();
//        producer.start();
//        producer1.start();
    }

    class Producer extends Thread {



        @Override
        public void run() {
            add();
        }

        public void add() {
            for (int i = 0; i < 200; i++) {
                BasePageBean request = new BasePageBean();
                request.setTargets("123-" + i);
                try {
                    logger.info("插入一个数据");
                    arrayBlockingQueue.put(request);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while (true) {
                if (!arrayBlockingQueue.isEmpty()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }

            logger.info("------> 全部数据处理完成 <-------");

        }
    }


    class Consumer extends Thread {

        @Override
        public void run() {
            get();
        }

        public void get() {

            while (true) {
                try {
                    if (arrayBlockingQueue.isEmpty()) {
                        logger.info("数据为空 ，锁定对象");
                        Thread.sleep(1000);
                    } else {
                        BasePageBean messageSendRequest = arrayBlockingQueue.poll(10, TimeUnit.SECONDS);
                        logger.info("获取一个对象：:{}", JSONObject.toJSONString(messageSendRequest));
                    }
                } catch (Exception e) {
                    logger.error("弹出数据失败:{},Message:{}", e.getClass(), e.getMessage());
                }
            }
        }
    }
}
