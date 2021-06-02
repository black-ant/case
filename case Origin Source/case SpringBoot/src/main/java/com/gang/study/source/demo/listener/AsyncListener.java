package com.gang.study.source.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Classname AsyncListener
 * @Description TODO
 * @Date 2021/5/31
 * @Created by zengzg
 */
@Component
public class AsyncListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 此处使用 Async 异步处理
     *
     * @param defaultEvent
     */
    @Async
    @EventListener
    public void doAsyncEvent(DefaultEvent defaultEvent) {
        logger.info("------> 通过异步监听 :[{}] , Thread is :[{}]<-------", defaultEvent.getSource(), Thread.currentThread().getId());
    }

}
