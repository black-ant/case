package com.security.demo.Utils;

import java.lang.reflect.Field;
import java.util.Date;

public class CommonUtils {
    /**
     * 遍历一个对象，打印数据与值
     *
     * @param {Object} obj 对象
     * @return
     */
    public static void reflect(Object obj) {
        if (obj == null) {
            return;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            // 字段值
            // String type
            try {
                String item = fields[j].getType().getName();
                switch (item) {
                    case "java.lang.Integer":
                        System.out.println(fields[j].getName() + " ========= " + (Integer) fields[j].get(obj));
                        break;
                    case "java.lang.Boolean":
                        System.out.println(fields[j].getName() + " ====== " + (Boolean) fields[j].get(obj));
                        break;
                    case "java.math.BigDecimal":
                        System.out.println(fields[j].getName() + " ==== " + fields[j].get(obj));
                        break;
                    case "double":
                        System.out.println(fields[j].getName() + " == " + (double) fields[j].getDouble(obj));
                        break;
                    case "java.util.Date":
                        System.out.println(fields[j].getName() + " == " + (Date) fields[j].get(obj));
                        break;
                    default:
                        System.out.println(fields[j].getName() + " ----- " + fields[j].get(obj));
                }
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 其他类型。。。
        }
    }

}
