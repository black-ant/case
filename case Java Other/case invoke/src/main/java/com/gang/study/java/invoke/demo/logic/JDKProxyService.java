package com.gang.study.java.invoke.demo.logic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Classname JDKProxy
 * @Description TODO
 * @Date 2020/6/2 23:14
 * @Created by zengzg
 */
public class JDKProxyService implements InvocationHandler {

    private ExcuterServiceImpl target;

    private JDKProxyService(ExcuterServiceImpl target) {
        this.target = target;
    }

    public static ExcuterServiceImpl newProxyInstance(ExcuterServiceImpl target) {
        return (ExcuterServiceImpl) Proxy
                .newProxyInstance(JDKProxyService.class.getClassLoader(),
                        new Class<?>[]{ExcuterServiceImpl.class},
                        new JDKProxyService(target));

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }
}
