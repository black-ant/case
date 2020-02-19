package com.gang.study.adplugin.utils;

import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @Classname ADUtilities
 * @Description TODO
 * @Date 2019/12/2 18:10
 * @Created by zengzg
 */
public final class ADSyncUtils {

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
