package com.gang.study.thread.future.gangthreadfuture.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Classname FutureSampleService
 * @Description Future 最基本使用
 * @Date 2021/2/24 17:21
 * @Created by zengzg
 */
@Service
public class FutureSampleService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in FutureSampleService <-------");

        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {

                long start = System.currentTimeMillis();
                Thread.sleep(100);
                long end = System.currentTimeMillis();

                long seed = end - start;
                System.out.println("seed=" + seed);

                return seed;
            }
        };

        List<Callable<Long>> tasks = new ArrayList<>();
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);

        int poolSize = Runtime.getRuntime().availableProcessors();
        System.out.println("poolSize=" + poolSize);
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        //
        List<Future<Long>> futures = executorService.invokeAll(tasks);

        long result = 0;
        for (Future<Long> future : futures) {
            result += future.get();
        }
        System.out.println("result=" + result);
        executorService.shutdown();
    }
}
