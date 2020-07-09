package com.gang.study.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @Classname ResourceUtils
 * @Description TODO
 * @Date 2020/7/9 16:55
 * @Created by zengzg
 */
public class ResourceUtils {


    public void read(String fileName) throws Exception {
        File file = new File("src/main/resources/" + fileName);
        InputStream in = new FileInputStream(file);

    }
}
