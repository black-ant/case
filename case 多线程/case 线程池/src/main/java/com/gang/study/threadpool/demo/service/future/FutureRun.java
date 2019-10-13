package com.gang.study.threadpool.demo.service.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureRun {

    public void run() {
        FutureTask<String> future = new FutureTask<String>(new FutureService("a"));
        ExecutorService executor = Executors.newFixedThreadPool(1);

        executor.submit(future);
        System.out.println("请求完毕！");
        try {
            System.out.println("我在睡觉别打扰我！2秒后回应");
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }

    }
}
