package com.gang.study.thread.consumerproduce.demo.logic4;

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

    }

    public static String get(Integer key) {
        String response = map.get(key);
        map.remove(key);
        return response;
    }
}
