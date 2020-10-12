package com.gang.study.pressure.demo.utils;

import java.util.Random;

/**
 * @Classname RandomUtils
 * @Description TODO
 * @Date 2020/10/10 9:37
 * @Created by zengzg
 */
public class RandomUtils {

    public static String getRandomName(String namePrefix) {
        Integer intPrefix = new Random().nextInt(99999);
        return namePrefix + intPrefix;
    }
}
