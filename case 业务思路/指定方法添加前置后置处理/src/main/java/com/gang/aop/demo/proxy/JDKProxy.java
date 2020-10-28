package com.gang.aop.demo.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname JDKProxy
 * @Description TODO
 * @Date 2020/10/28 17:24
 * @Created by zengzg
 */
public class JDKProxy implements InvocationHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExInterface invokeObj;
    private Class<?> aopObject;
    private Map<String, Method> aopMethodMap = new HashMap<>();

    /**
     * 创建代理类
     *
     * @return
     */
    public ExInterface createProxy(ExInterface target, Class<?> aopObject) {
        this.invokeObj = (ExInterface) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
        this.aopObject = aopObject;
        List<Method> method = Arrays.asList(aopObject.getMethods());
        method.forEach(item -> {
            aopMethodMap.put(item.getName(), item);
        });
        return invokeObj;
    }

    /**
     * 调用被代理类(目标对象)的任意方法都会触发invoke方法
     *
     * @param proxy  代理类
     * @param method 被代理类的方法
     * @param args   被代理类的方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Method methodAop = aopMethodMap.get(method.getName());
        //过滤不需要该业务的方法
        if ("execute".equals(method.getName())) {
            logger.info("------> execute <-------");
            List<String> parameters =
                    Arrays.asList(methodAop.getParameters()).stream().map(p -> p.getName()).collect(Collectors.toList());

            List argsNew = new ArrayList();
            for (int i = 0; i < method.getParameters().length; i++) {
                if (parameters.contains(method.getParameters()[i].getName())) {
                    argsNew.add(args[i]);
                }
            }
            methodAop.invoke(aopObject.newInstance(), argsNew.toArray());
        } else if ("delete".equals(method.getName())) {
            logger.info("------> delete <-------");
        }
        //如果不需要增强直接执行原方法
        return method.invoke(invokeObj, args);
    }


}

