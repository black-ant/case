package com.gang.study.myfilter.demo.listener;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class MyListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("------> contextInitialized :{} <-------", JSONObject.toJSONString(sce));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("------> ServletContextEvent :{} <-------", JSONObject.toJSONString(sce));
    }
}
