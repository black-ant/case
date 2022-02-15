package com.gang.processor.demo.manager;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname BeanManager
 * @Description TODO
 * @Date 2021/10/7
 * @Created by zengzg
 */
@Component
public class BeanManager {

    public static Map<String, TypeBean> typeOne = new ConcurrentHashMap();

    public static Map<String, TypeBean> typeTwo = new ConcurrentHashMap();

    public static Map<String, TypeBean> getTypeOne() {
        return typeOne;
    }

    public static void setTypeOne(Map<String, TypeBean> typeOne) {
        BeanManager.typeOne = typeOne;
    }

    public static Map<String, TypeBean> getTypeTwo() {
        return typeTwo;
    }

    public static void setTypeTwo(Map<String, TypeBean> typeTwo) {
        BeanManager.typeTwo = typeTwo;
    }

    public void add(String key, TypeBean typeBean) {
        typeOne.put(key, typeBean);
    }
}
