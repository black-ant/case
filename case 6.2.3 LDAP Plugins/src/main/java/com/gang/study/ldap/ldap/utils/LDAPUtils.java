package com.gang.study.ldap.ldap.utils;

import java.util.Random;

/**
 * @Classname LDAPUtils
 * @Description TODO
 * @Date 2019/12/2 19:27
 * @Created by zengzg
 */
public class LDAPUtils {

    /**
     * @param type     构建类型
     * @param baseData 基础数据
     * @Description:构建动态代码
     */
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
