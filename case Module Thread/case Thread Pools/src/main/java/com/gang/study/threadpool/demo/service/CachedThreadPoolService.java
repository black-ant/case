package com.gang.study.threadpool.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Classname CachedThreadPoolService
 * @Description TODO
 * @Date 2019/11/1 11:07
 * @Created by ant-black 1016930479@qq.com
 */
@Service
public class CachedThreadPoolService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() {
        //        cache();
//        newRunable();
    }

    /**
     * 通过 new ThreadPoolExecutor 可以很有效的控制线程池的使用 , 根据业务情况进行处理 ,  避免 OOM
     */
    public void theadPoolCreate() {

        // 构造一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 6, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3));

        // 对比 newCachedThreadPool
        ThreadPoolExecutor threadPool2 = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

    }


    /**
     * Executors 创建线程虽然很简单 , 但是可能导致 OOM , 简单但是不推荐
     */
    public void cache() {
        //        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        //        ThreadFactory threadFactory = new ThreadFactory();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        executor.submit(() -> {
            logger.info("------> this is run 11111 <-------");
            Thread.sleep(1000);

            return null;
        });

        executor.submit(() -> {
            logger.info("------> this is run 22222 <-------");
            Thread.sleep(1000);

            return null;
        });
        executor.submit(() -> {
            logger.info("------> this is run 33333 <-------");
            Thread.sleep(1000);
            return null;
        });

        executor.submit(() -> {
            logger.info("------> this is run 44444 <-------");
            Thread.sleep(1000);
            return null;
        });

        executor.submit(() -> {
            logger.info("------> this is run 5555 <-------");
            Thread.sleep(1000);
            return null;
        });


        executor.submit(() -> {
            logger.info("------> this is run 66666 <-------");
            Thread.sleep(1000);
            return null;
        });

        executor.submit(() -> {
            logger.info("------> this is run 77777 <-------");
            Thread.sleep(1000);
            return null;
        });
        logger.info("------> poolsize :{} <-------", executor.getPoolSize());
        logger.info("------> queue size :{} <-------", executor.getQueue().size());
    }

    public void newRunable() {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Runnable() {
            @Override
            public void run() {
                logger.info("------> 批量操作全部完成 <-------");
            }
        });

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Trans trans = new Trans(1);
        for (int i = 0; i < 10; i++) {
            executor.execute(new TaskThread(cyclicBarrier, "this is i :" + i, trans));
        }
    }

    class Trans extends ReentrantReadWriteLock {

        private volatile Integer size;

        public Trans(Integer size) {
            this.size = size;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }
    }

    class TaskThread implements Runnable {

        private CyclicBarrier barrier;
        private String msg;
        private Trans size;

        TaskThread(CyclicBarrier barrier, String msg, Trans size) {
            this.barrier = barrier;
            this.msg = msg;
            this.size = size;
        }

        @Override
        public void run() {
            logger.info("------> init Future Thread :{} -- msg :{} <-------", Thread.currentThread().getId(), msg);
            try {
                size.writeLock();
                size.setSize(size.getSize() + 2);
                logger.info("------> this is size :{} <-------", size.getSize());
            } catch (Exception e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            } finally {
                try {
                    barrier.await();
                } catch (BrokenBarrierException | InterruptedException e) {
                    logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
                }

            }
        }
    }
}
