package com.gang.cloud.resilience4j.demo.service;

import com.gang.cloud.resilience4j.demo.utils.CircuitBreakerUtil;
import com.gang.cloud.resilience4j.demo.utils.RetryUtil;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeoutException;

/**
 * @Classname CircuitBreakerServiceImpl
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
@Component
public class CircuitBreakerServiceImpl {

    @Autowired
    private RemoteServiceConnector remoteServiceConnector;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    private RetryRegistry retryRegistry;

    public String circuitBreakerRetryAOP() throws TimeoutException, InterruptedException {
        String result = remoteServiceConnector.process();
        RetryUtil.getRetryStatus("执行结束：", retryRegistry.retry("backendA"));
        CircuitBreakerUtil
                .getCircuitBreakerStatus("执行结束：", circuitBreakerRegistry.circuitBreaker("backendA"));
        return result;
    }
}
