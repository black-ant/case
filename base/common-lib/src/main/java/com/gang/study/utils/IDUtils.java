package com.gang.study.utils;

import java.util.Random;

/**
 * @Classname IDUtils
 * @Description TODO
 * @Date 2020/1/12 21:01
 * @Created by zengzg
 */
public class IDUtils {

    private static final Integer RANDOM_SIZE = 999999;

    public static String crateId() {
        return crateId(RANDOM_SIZE);
    }

    public static String crateId(Integer size) {
        return String.valueOf(createId(size));
    }

    public static Integer createId(Integer size) {
        return new Random().nextInt(size);
    }

    public static String createEmail() {
        return crateId() + "@qgang.com";
    }

    public static String createMobil() {
        return "123046400" + crateId(99);
    }
}
