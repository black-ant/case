package com.gang.cloud.resilience4j.demo.utils;

import io.github.resilience4j.retry.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname RetryUtil
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
public class RetryUtil {

    private static Logger log = LoggerFactory.getLogger(RetryUtil.class);

    /**
     * @Description: 获取重试的状态
     */
    public static void getRetryStatus(String time, Retry retry){
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
    public static void addRetryListener(Retry retry){
        retry.getEventPublisher()
                .onSuccess(event -> log.info("服务调用成功：" + event.toString()))
                .onError(event -> log.info("服务调用失败：" + event.toString()))
                .onIgnoredError(event -> log.info("服务调用失败，但异常被忽略：" + event.toString()))
                .onRetry(event -> log.info("重试：第" + event.getNumberOfRetryAttempts() + "次"))
        ;
    }
}
