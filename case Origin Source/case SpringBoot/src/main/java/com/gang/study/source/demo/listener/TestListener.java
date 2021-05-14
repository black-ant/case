package com.gang.study.source.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Classname SelfListener
 * @Description TODO
 * @Date 2021/5/14
 * @Created by zengzg
 */
@Component
public class TestListener implements ApplicationListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        logger.info("------> TestListener application Event  <-------");
    }
}
