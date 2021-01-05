package com.gang.study.myfilter.demo.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("------> sessionCreated <-------");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("------> sessionDestroyed  <-------");
    }
}
