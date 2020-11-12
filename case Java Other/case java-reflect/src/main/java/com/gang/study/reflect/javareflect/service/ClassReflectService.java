package com.gang.study.reflect.javareflect.service;

import com.gang.study.reflect.javareflect.base.BaseInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @Classname ClassReflectService
 * @Description TODO
 * @Date 2020/11/12 13:50
 * @Created by zengzg
 */
@Component
public class ClassReflectService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        reflectClass();
    }

    /**
     * 反射获取指定接口的实现类
     */
    public void reflectClass() {
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        getAllSubclassOfTestInterface();

    }


    /**
     * 方案一 : 获取 ClassLoader 加载的所有类 , 然后筛选出对应的数据 (麻烦)
     */
    @SuppressWarnings("unchecked")
    private void getAllSubclassOfTestInterface() {
        Field field = null;
        Vector v = null;
        List<Class<BaseInterface>> allSubclass = new ArrayList<Class<BaseInterface>>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> classOfClassLoader = classLoader.getClass();
        try {
            Class.forName(BaseInterface.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "无法获取到TestInterface的Class对象!查看包名,路径是否正确");
        }
        while (classOfClassLoader != ClassLoader.class) {
            classOfClassLoader = classOfClassLoader.getSuperclass();
        }
        try {
            field = classOfClassLoader.getDeclaredField("classes");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(
                    "无法获取到当前线程的类加载器的classes域!");
        }
        field.setAccessible(true);
        try {
            v = (Vector) field.get(classLoader);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "无法从类加载器中获取到类属性!");
        }
        for (int i = 0; i < v.size(); ++i) {
            Class<?> c = (Class<?>) v.get(i);
            logger.info("------> this is c :{} <-------", c.getSimpleName());
        }
    }
}
