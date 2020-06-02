package com.gang.study.java.invoke.demo.logic;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Classname CglibProxyService
 * @Description TODO
 * @Date 2020/6/2 23:17
 * @Created by zengzg
 */
public class CglibProxyService implements MethodInterceptor {

    private CglibProxyService() {
    }

    public static <T extends ExcuterServiceImpl> ExcuterServiceImpl newProxyInstance(Class<T> targetInstanceClazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetInstanceClazz);
        enhancer.setCallback(new CglibProxyService());
        return (ExcuterServiceImpl) enhancer.create();
    }

    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        return proxy.invokeSuper(obj, args);
    }
}
