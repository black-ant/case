package com.gang.study.io.demo.streambyte;

import com.gang.study.io.demo.api.IBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @Classname FileStreamService
 * @Description FileOutputStream + FileInputStream
 * @Date 2020/7/30 10:04
 * @Created by zengzg
 */
@Component
public class FileStreamService implements IBaseService<String> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String path = "D:\\java\\workspace\\git\\case\\case Java Other\\case Java IO\\src\\main\\resources\\";

    @Override
    public String output(String config) throws Exception {
        // 方式一 : 生成 File 后生成
        File file = new File(path + "output.txt");
        // 创建一个文件


        FileInputStream fileExample1 = new FileInputStream(path + "resourceDoc.txt");


        return "";
    }

    @Override
    public void input(String s) throws Exception {

        // 方式一 : 全路径获取
        FileInputStream fileExample1 = new FileInputStream("D:\\java\\workspace\\git\\case\\case Java Other\\case Java IO\\src\\main\\resources\\resourceDoc.txt");
        show(fileExample1);

        // 方式二 :  File -> FileStream
        File file = new File(this.getClass().getResource("/resourceDoc.txt").getPath());
        FileInputStream fileExample2 = new FileInputStream(file);
        show(fileExample2);
    }

    /**
     * @param fileInputStream
     * @throws Exception
     */
    public void show(FileInputStream fileInputStream) throws Exception {
        //数据中转站 临时缓冲区
        byte[] buf = new byte[1024];
        int length = 0;
        while ((length = fileInputStream.read(buf)) != -1) {
            System.out.println(new String(buf, 0, length));
        }
    }
}
