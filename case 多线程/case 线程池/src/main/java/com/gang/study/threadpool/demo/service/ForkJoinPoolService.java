package com.gang.study.threadpool.demo.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ForkJoinPool;

/**
 * @Classname ForkJoinPoolService
 * @Description TODO
 * @Date 2019/11/1 11:38
 * @Created by ant-black 1016930479@qq.com
 */
@Service
public class ForkJoinPoolService {

    public void run() {

    }

    public void fork() {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
//        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
//        ForkJoinPool forkJoinPool = PoolUtil.forkJoinPool;
    }
}
