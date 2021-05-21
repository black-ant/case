package com.gang.study.java.invoke.demo.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理子对象
 */
public class ProxyTarget {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String username;

    public void init() {
        logger.info("------> 代理执行 Init 方法 <-------");
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
