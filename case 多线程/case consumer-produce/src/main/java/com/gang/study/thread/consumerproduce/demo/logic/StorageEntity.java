package com.gang.study.thread.consumerproduce.demo.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname StorageEntity
 * @Description TODO
 * @Date 2020/4/19 13:35
 * @Created by zengzg
 */
public class StorageEntity {

    private static Map<Integer, String> map = new HashMap<>();

    public static void add(Integer key, String value) {
        map.put(key, value);
        System.out.println(Thread.currentThread().getId() + "  add key : " + key + "--value :" + value);

    }

    public synchronized static void get(Integer key) {
        System.out.println(Thread.currentThread().getId() + "  get key : " + key + "=" + map.get(key));
    }
}
