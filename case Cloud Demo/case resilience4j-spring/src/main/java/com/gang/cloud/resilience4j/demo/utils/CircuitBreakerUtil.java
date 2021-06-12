package com.gang.cloud.resilience4j.demo.utils;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname CircuitBreakerUtil
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
public class CircuitBreakerUtil {

    private static Logger log = LoggerFactory.getLogger(CircuitBreakerUtil.class);

    /**
     * @Description: 获取重试的状态
     */
    public static void getRetryStatus(String time, Retry retry) {
        Retry.Metrics metrics = retry.getMetrics();
        long failedRetryNum = metrics.getNumberOfFailedCallsWithRetryAttempt();
        long failedNotRetryNum = metrics.getNumberOfFailedCallsWithoutRetryAttempt();
        long successfulRetryNum = metrics.getNumberOfSuccessfulCallsWithRetryAttempt();
        long successfulNotyRetryNum = metrics.getNumberOfSuccessfulCallsWithoutRetryAttempt();

        log.info(time + "state=" + " metrics[ failedRetryNum=" + failedRetryNum +
                ", failedNotRetryNum=" + failedNotRetryNum +
                ", successfulRetryNum=" + successfulRetryNum +
                ", successfulNotyRetryNum=" + successfulNotyRetryNum +
                " ]"
        );
    }

    /**
     * @Description: 监听重试事件
     */
    public static void addRetryListener(Retry retry) {
        retry.getEventPublisher()
                .onSuccess(event -> log.info("服务调用成功：" + event.toString()))
                .onError(event -> log.info("服务调用失败：" + event.toString()))
                .onIgnoredError(event -> log.info("服务调用失败，但异常被忽略：" + event.toString()))
                .onRetry(event -> log.info("重试：第" + event.getNumberOfRetryAttempts() + "次"))
        ;
    }

    /**
     * @Description: 获取熔断器的状态
     */
    public static void getCircuitBreakerStatus(String time, CircuitBreaker circuitBreaker) {
        CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
        // Returns the failure rate in percentage.
        float failureRate = metrics.getFailureRate();
        // Returns the current number of buffered calls.
        int bufferedCalls = metrics.getNumberOfBufferedCalls();
        // Returns the current number of failed calls.
        int failedCalls = metrics.getNumberOfFailedCalls();
        // Returns the current number of successed calls.
        int successCalls = metrics.getNumberOfSuccessfulCalls();
        // Returns the max number of buffered calls.
        int maxBufferCalls = metrics.getNumberOfBufferedCalls();
        // Returns the current number of not permitted calls.
        long notPermittedCalls = metrics.getNumberOfNotPermittedCalls();

        log.info(time + "state=" + circuitBreaker.getState() + " , metrics[ failureRate=" + failureRate +
                ", bufferedCalls=" + bufferedCalls +
                ", failedCalls=" + failedCalls +
                ", successCalls=" + successCalls +
                ", maxBufferCalls=" + maxBufferCalls +
                ", notPermittedCalls=" + notPermittedCalls +
                " ]"
        );
    }

    /**
     * @Description: 监听熔断器事件
     */
    public static void addCircuitBreakerListener(CircuitBreaker circuitBreaker) {
        circuitBreaker.getEventPublisher()
                .onSuccess(event -> log.info("服务调用成功：" + event.toString()))
                .onError(event -> log.info("服务调用失败：" + event.toString()))
                .onIgnoredError(event -> log.info("服务调用失败，但异常被忽略：" + event.toString()))
                .onReset(event -> log.info("熔断器重置：" + event.toString()))
                .onStateTransition(event -> log.info("熔断器状态改变：" + event.toString()))
                .onCallNotPermitted(event -> log.info(" 熔断器已经打开：" + event.toString()))
        ;
    }
}
