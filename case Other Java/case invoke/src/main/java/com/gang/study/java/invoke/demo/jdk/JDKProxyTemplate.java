package com.gang.study.java.invoke.demo.jdk;


import java.lang.reflect.Proxy;

/**
 * @Classname JDKProxyTemplate
 * @Description 一个完整的 JDK Proxy 代理的流程
 * @Date 2021/5/20
 * @Created by zengzg
 */
public class JDKProxyTemplate {

    /**
     * @param target
     * @return
     */
    public static ProxyInvoke newProxyInstance(ProxyInvoke target) {
        return (ProxyInvoke) Proxy
                .newProxyInstance(JDKProxyTemplate.class.getClassLoader(),
                        new Class<?>[]{ProxyInvoke.class},
                        new ProxyInvoke(target));
    }
}

