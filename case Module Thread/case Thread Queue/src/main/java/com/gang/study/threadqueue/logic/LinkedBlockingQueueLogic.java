package com.gang.study.threadqueue.logic;

import com.gang.study.threadqueue.to.UserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname LinkedBlockingQueueLogic
 * @Description TODO
 * @Date 2020/7/23 17:51
 * @Created by zengzg
 */
@Service
public class LinkedBlockingQueueLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        LinkedBlockingQueue<UserTO> linkedBlockingQueue = new LinkedBlockingQueue<>();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            for (int i = 0; i < 3000000; i++) {
                UserTO userTO = new UserTO();
                linkedBlockingQueue.offer(userTO);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("------> 全部插入完成 <-------");
        });


        ThreadPoolExecutor executor2 = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executor2.submit(() -> {
            for (int i = 0; i < 3010001; i++) {
                UserTO userTO = linkedBlockingQueue.poll();
//                logger.info("------> this is ：{} <-------", userTO);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("------> 全部获取完成 <-------");
        });
    }


}
