package com.gang.file.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Classname StreamService
 * @Description https://blog.csdn.net/huo920/article/details/109857987
 * @Date 2021/7/23
 * @Created by zengzg
 */
public class StreamService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void read(File file) {


    }

    public static void input(String s) throws Exception {

        // 方式一 : 全路径获取
        FileInputStream fileExample1 = new FileInputStream(s);
        show(fileExample1);
    }

    public static void inputByFile(File file) throws Exception {
        // 方式二 :  File -> FileStream
        // File file = new File(this.getClass().getResource("/resourceDoc.txt").getPath());
        FileInputStream fileExample2 = new FileInputStream(file);
        show(fileExample2);
    }

    /**
     * @param fileInputStream
     * @throws Exception
     */
    public static void show(FileInputStream fileInputStream) throws Exception {
        //数据中转站 临时缓冲区
        byte[] buf = new byte[1024];
        int length = 0;
        while ((length = fileInputStream.read(buf)) != -1) {
            System.out.println(new String(buf, 0, length));
        }
    }
}
