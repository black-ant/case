package com.gang.study.io.demo.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ReadWriteService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    String path = "D:\\java\\workspace\\git\\case\\case Java Other\\case Java IO\\doc\\test.txt";
    String dest = "D:\\java\\workspace\\git\\case\\case Java Other\\case Java IO\\doc\\test1.txt";

    public void run() {
        try {
            write(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String path) throws IOException {
        //输出内容的文件数据源
        File f = new File(path);
        PrintWriter pw = new PrintWriter(f);
        //把指定内容打印至数据源中
        pw.println("AAAAAAAAA");
        pw.println("BBBBBBBBB");
        pw.println("CCCCCCCCC");
        pw.flush();
        System.out.println("使用PrintWriter写入数据完成");
        System.out.println("==========读取写入的数据==========");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String s = null;
        StringBuilder sb = new StringBuilder();//一个可变字符串
        while ((s = br.readLine()) != null) {
            sb.append(s); //把读取的字符串组合起来
        }
        System.out.println(sb);
        br.close();
        pw.close();
    }


}
