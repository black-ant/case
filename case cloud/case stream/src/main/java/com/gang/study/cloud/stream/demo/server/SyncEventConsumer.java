package com.gang.study.cloud.stream.demo.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Classname SyncEventConsumer
 * @Description TODO
 * @Date 2019/11/25 14:54
 * @Created by zengzg
 */
public interface SyncEventConsumer {
    String CHANNEL = "SyncEventConsumer";

    @Input("SyncEventConsumer")
    SubscribableChannel consumer();
}
