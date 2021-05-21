package com.gang.study.java.invoke.demo.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理子对象
 */
public class ProxyInvoke implements InvocationHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Object target;

    public ProxyInvoke(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("------> this is in invoke ProxyTarget <-------");
        if (proxy instanceof ProxyInvoke) {
            ProxyInvoke thisObj = (ProxyInvoke) proxy;
        }
        return null;
    }
}
