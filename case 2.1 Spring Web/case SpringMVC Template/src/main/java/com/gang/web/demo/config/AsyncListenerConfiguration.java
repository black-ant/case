package com.gang.web.demo.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Classname AsyncListenerConfiguration
 * @Description 此处为异步操作方式
 * @Date 2021/5/31
 * @Created by zengzg
 */
@Configuration
@EnableAsync
public class AsyncListenerConfiguration implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor poolTE = new ThreadPoolTaskExecutor();
        // 设置线程池的数目
        poolTE.setCorePoolSize(10);
        poolTE.setMaxPoolSize(20);
        poolTE.setQueueCapacity(50);
        poolTE.initialize();
        return poolTE;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
