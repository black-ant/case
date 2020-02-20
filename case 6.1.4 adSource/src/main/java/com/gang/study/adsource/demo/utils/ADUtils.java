package com.gang.study.adsource.demo.utils;

import com.gang.study.adsource.demo.to.ObjectClass;

/**
 * @Classname ADUtils
 * @Description TODO
 * @Date 2020/2/19 14:13
 * @Created by zengzg
 */
public class ADUtils {

    public static String getGUID(String displayGUID) {
        return HexUtils.getEscaped(GUIDUtils.getGuidAsByteArray(displayGUID));
    }

    public static String editOclass(ObjectClass objclass) {
        String oclass = "*";
        if (objclass.is(ObjectClass.GROUP_NAME) || objclass.is("__GROUP__")) {
            oclass = "group";
        } else if (objclass.is(ObjectClass.ACCOUNT_NAME) || objclass.is("__ACCOUNT__")) {
            oclass = "user";
        } else if (objclass.is("__ORGANIZATION__") || objclass.is("organizationalUnit")
                || objclass.is("ORGANIZATION")) {
            oclass = "organizationalUnit";
        } else if (objclass.is(ObjectClass.ALL_NAME)) {
            oclass = "*";
        } else {
            oclass = "*";
        }
        return oclass;
    }

    /**
     * -> 编辑搜索字段
     *
     * @param searchType
     * @return
     */
    public static String editsearchType(String searchType) {
        String searchFiled = "cn";
        if ("CN".equals(searchType) || "cn".equals(searchType)) {
            searchFiled = "cn";
        } else if ("dn".equals(searchType) || "distinguishedName".equals(searchType)) {
            searchFiled = "distinguishedName";
        } else if ("ou".equals(searchType)) {
            searchFiled = "OU";
        } else if ("usercode".equals(searchType)) {
            searchFiled = "sAMAccountName";
        }
        return searchFiled;
    }
}
