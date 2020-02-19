package com.gang.study.adsource.demo.utils;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

/**
 * @Classname ADSyncUtils
 * @Description TODO
 * @Date 2020/2/19 20:42
 * @Created by zengzg
 */
public final class ADSyncUtils {

    private static Logger LOG = LoggerFactory.getLogger(ADSyncUtils.class);

    private ADSyncUtils() {

    }

    public static String dataCreateUtil(String type, String baseData) {
        String cdata = "111111";
        switch (type) {
            case "name":
                cdata = baseData + new Random().nextInt(666);
                break;
            default:
                break;
        }
        return cdata;
    }


}
