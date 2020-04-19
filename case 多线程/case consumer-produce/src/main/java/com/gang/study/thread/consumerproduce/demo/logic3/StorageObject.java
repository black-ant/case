package com.gang.study.thread.consumerproduce.demo.logic3;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname ValueObject
 * @Description TODO
 * @Date 2020/4/19 16:41
 * @Created by zengzg
 */
public class StorageObject {

    private static Map<Integer, String> map = new HashMap<>();

    public static void add(Integer key, String value) {
        map.put(key, value);
        //        System.out.println(Thread.currentThread().getId() + "  add key : " + key + "--value :" + value);

    }

    public static String get(Integer key) {
        //        System.out.println(Thread.currentThread().getId() + "  get key : " + key + "=" + map.get(key));
        return map.get(key);
    }

    public static Integer size() {
        return map.size();
    }
}
