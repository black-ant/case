package com.gang.study.source.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Classname MySelfListener
 * @Description TODO
 * @Date 2021/5/14
 * @Created by zengzg
 */
@Component
public class DefaultListener implements ApplicationListener<DefaultEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(DefaultEvent event) {
        logger.info("------> DefaultEvent Listner , Properties [{}] <-------", String.valueOf(event.getSource()));
        logger.info("------> Listener Thread 情况 :[{}] <-------", Thread.currentThread().getId());

    }
}
