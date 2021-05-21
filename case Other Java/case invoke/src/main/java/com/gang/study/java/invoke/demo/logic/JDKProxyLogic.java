package com.gang.study.java.invoke.demo.logic;

import com.gang.study.java.invoke.demo.service.ExcuterServiceImpl;
import com.gang.study.java.invoke.demo.service.IExcuterService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Classname JDKProxy
 * @Description TODO
 * @Date 2020/6/2 23:14
 * @Created by zengzg
 */
public class JDKProxyLogic implements InvocationHandler {

    private IExcuterService target;

    private JDKProxyLogic(IExcuterService target) {
        this.target = target;
    }

    public static ExcuterServiceImpl newProxyInstance(IExcuterService target) {
        return (ExcuterServiceImpl) Proxy
                .newProxyInstance(JDKProxyLogic.class.getClassLoader(),
                        new Class<?>[]{ExcuterServiceImpl.class},
                        new JDKProxyLogic(target));

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }
}
