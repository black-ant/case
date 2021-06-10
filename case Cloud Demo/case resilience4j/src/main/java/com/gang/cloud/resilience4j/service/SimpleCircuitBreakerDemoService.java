package com.gang.cloud.resilience4j.service;

import com.gang.cloud.resilience4j.exceptions.DefaultTransactionException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Classname SimpleCircuitBreakerDemoService
 * @Description resilience4j 断路器使用
 * @Date 2021/6/10
 * @Created by zengzg
 * @ https://www.baeldung.com/resilience4j
 */
@Component
public class SimpleCircuitBreakerDemoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestService testService;

    public void run() {
        // 发起基础功能
        doSimpleRequest();

    }

    /**
     * 构建一个 CircuitBreaker , 用于发起远程认证
     *
     * @return
     */
    public CircuitBreaker buildCircuitBreaker() {

        // Step 1 : 创建一个 CircuitBreakerConfig
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .slidingWindowSize(2)
                .recordExceptions(IOException.class, TimeoutException.class)
                .ignoreExceptions(DefaultTransactionException.class)
                .build();

        // 通过配置获取 CircuitBreakerRegistry
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);


        // Step 2 : 创建一个 CircuitBreaker
        return circuitBreakerRegistry.circuitBreaker("YouCircuitBreakerName");
    }

    /**
     * 通过 Function 函数发起调用
     */
    public void doSimpleRequest() {
        Function<String, String> decorated = CircuitBreaker
                .decorateFunction(buildCircuitBreaker(), testService::doSomething);

        TestService.setCount(0);
        for (int i = 0; i < 10; i++) {
            try {
                decorated.apply(String.valueOf(i));
                Thread.sleep(500);
            } catch (Exception ignore) {
                logger.error("E----> error :{} -- content :{}", ignore.getClass(), ignore.getMessage());
            }
        }
    }


    public void do2() {
        // Step 3 ： 构建修饰器
        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(buildCircuitBreaker(), testService::doSomething);

        // Step 4 : 发起认证
        String result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery").get();

        // Step 5 :


        // 不想装饰 lambda 表达式时，只需执行它并通过一个 CircuitBreaker 保护调用
        // String result = circuitBreaker.executeSupplier(backendService::doSomething);
    }
}
