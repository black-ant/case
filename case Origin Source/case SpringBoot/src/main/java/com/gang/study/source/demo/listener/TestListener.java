package com.gang.study.source.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname SelfListener
 * @Description TODO
 * @Date 2021/5/14
 * @Created by zengzg
 */
@Component
public class TestListener implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("------> Default Event Publish Start >>>>>  <-------");

        ListenerTranTO tranTO = new ListenerTranTO();

        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("info", "This is in Info");
        infoMap.put("message", "Listener Success");

        tranTO.setEventInfo(infoMap);
        tranTO.setEventName("DefaultListener");

        context.publishEvent(new DefaultEvent(tranTO));

        logger.info("------> Default Event Publish End >>>>> [{}]  <-------", tranTO.toString());
    }
}
