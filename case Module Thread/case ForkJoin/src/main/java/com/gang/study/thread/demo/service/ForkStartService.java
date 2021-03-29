package com.gang.study.thread.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @Classname ForkStartService
 * @Description TODO
 * @Date 2020/6/29 14:24
 * @Created by zengzg
 */
//@Service
public class ForkStartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("------> this is in start ForkStartService <-------");

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinPoolService countTask = new ForkJoinPoolService(1, 200);
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(countTask);
        System.out.println(forkJoinTask.get());


        ForkJoinPool forkJoinPool1 = new ForkJoinPool();
        FileForkJoinService countTask1 = new FileForkJoinService();
        ForkJoinTask<Integer> forkJoinTask1 = forkJoinPool.submit(countTask);
        System.out.println(forkJoinTask.get());
    }
}
