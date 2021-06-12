package com.gang.cloud.resilience4j.demo.service;

import com.gang.cloud.resilience4j.demo.client.IRemoteService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeoutException;

/**
 * @Classname RemoteServiceConnector
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
@Component
public class RemoteServiceConnector {

    @Autowired
    private IRemoteService remoteService;

    @CircuitBreaker(name = "backendA", fallbackMethod = "fallBack")
    @Retry(name = "backendA", fallbackMethod = "fallBack")
    public String process() throws TimeoutException, InterruptedException {
        return remoteService.process();
    }
}
