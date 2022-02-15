package com.gang.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @Classname CglibStartService
 * @Description TODO
 * @Date 2021/10/6
 * @Created by zengzg
 */

public class CglibStartService {

    private static Logger logger = LoggerFactory.getLogger(CglibStartService.class);

    public static void main(String[] args) throws Exception {

        logger.info("------> this is System Property :{} <-------", System.getProperty("user.dir"));

        /** 开启 保存cglib生成的动态代理类类文件*/
        saveGeneratedCGlibProxyFiles(System.getProperty("user.dir"));


        /** 第一种方法: 创建cglib 代理类 start */
        // 创建加强器，用来创建动态代理类
        Enhancer enhancer = new Enhancer();
        // 为代理类指定需要代理的类，也即是父类
        enhancer.setSuperclass(CglibService.class);
        // new 一个新的方法拦截器
        CglibMethodInterceptor cglibMethodInterceptor = new CglibMethodInterceptor();

        // 设置方法拦截器回调引用，对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept() 方法进行拦截
        enhancer.setCallback(cglibMethodInterceptor);

        // 获取动态代理类对象并返回
        CglibService cglibService = (CglibService) enhancer.create();

        /** 创建cglib 代理类 end */
        cglibService.run();
    }

    /**
     * 设置保存Cglib代理生成的类文件。
     *
     * @throws Exception
     */
    public static void saveGeneratedCGlibProxyFiles(String dir) throws Exception {
        Field field = System.class.getDeclaredField("props");
        field.setAccessible(true);
        Properties props = (Properties) field.get(null);
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, dir);//dir为保存文件路径
        props.put("net.sf.cglib.core.DebuggingClassWriter.traceEnabled", "true");
    }
}
