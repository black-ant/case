package com.gang.study.java.invoke.demo.logic;

import com.gang.study.java.invoke.demo.service.ExcuterServiceImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Classname CglibProxyLogic
 * @Description TODO
 * @Date 2020/6/2 23:17
 * @Created by zengzg
 */
public class CglibProxyLogic implements MethodInterceptor {

    private CglibProxyLogic() {
    }

    public static <T extends ExcuterServiceImpl> ExcuterServiceImpl newProxyInstance(Class<T> targetInstanceClazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetInstanceClazz);
        enhancer.setCallback(new CglibProxyLogic());
        return (ExcuterServiceImpl) enhancer.create();
    }

    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        return proxy.invokeSuper(obj, args);
    }
}
