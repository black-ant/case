package com.gang.study.easyexcel.demo.utils;

import java.io.File;

/**
 * @Classname FileUtils
 * @Description TODO
 * @Date 2020/4/12 23:29
 * @Created by zengzg
 */
public class FileUtils {

    public static String getPath() {
        return FileUtils.class.getResource("/").getPath();
    }

    public static File createNewFile(String pathName) {
        File file = new File(getPath() + pathName);
        if (file.exists()) {
            file.delete();
        } else {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    public static File readFile(String pathName) {
        return new File(getPath() + pathName);
    }
}
