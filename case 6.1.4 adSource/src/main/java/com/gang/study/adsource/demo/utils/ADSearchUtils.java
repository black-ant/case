package com.gang.study.adsource.demo.utils;

import com.gang.study.adsource.demo.to.ObjectClass;
import com.gang.study.adsource.demo.type.ADSearchType;

/**
 * @Classname ADSearchUtils
 * @Description TODO
 * @Date 2020/2/20 14:16
 * @Created by zengzg
 */
public class ADSearchUtils {

    public static String getNativeFilter(ADSearchType adSearchType, String key, String value, ObjectClass objectClass) {
        StringBuilder backFilter = new StringBuilder();
        backFilter.append("(");
        switch (adSearchType) {
            case EQUALS:
                backFilter.append("&");
                backFilter.append(getSearchClassFilter(objectClass)).append(getEqualsFilter(key, value));
                break;
            case ALL:
                backFilter.append("&");
                backFilter.append(getSearchClassFilter(objectClass));
                break;
            case OR:
                backFilter.append("|");
                backFilter.append(getSearchClassFilter(objectClass)).append(getEqualsFilter(key, value));
                break;
            default:
                ;

        }
        backFilter.append(")");
        return backFilter.toString();
    }

    public static String getSearchClassFilter(ObjectClass objectClass) {
        return "(objectClass=" + ADUtils.editOclass(objectClass) + ")";
    }

    public static String getEqualsFilter(String key, String value) {
        if ("objectGUID".equals(key)) {
            return "(" + key + "=" + ADUtils.getGUID(value) + ")";
        }
        return "(" + key + "=" + value + ")";
    }


}
