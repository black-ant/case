package com.gang.study.io.demo.service;

import org.springframework.stereotype.Service;

import java.io.*;

/**
 * IO 流对大文件的处理
 */
@Service
public class BigFile {

    String path = "C:\\Users\\10169\\Desktop\\log\\log\\cic-sso-server-2.log";

    public void run() {
        splitFileDemo(new File(path), 3);
    }

    //文件分割的方法（方法内传入要分割的文件路径以及要分割的份数）
    private static void splitFileDemo(File src, int m) {
        if (src.isFile()) {
            //获取文件的总长度
            long l = src.length();
            //获取文件名
            String fileName = src.getName().substring(0, src.getName().indexOf("."));
            //获取文件后缀
            String endName = src.getName().substring(src.getName().lastIndexOf("."));
            System.out.println(endName);
            InputStream in = null;
            try {
                in = new FileInputStream(src);
                for (int i = 1; i <= m; i++) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(src.getParent()).append("\\").append(fileName)
                            .append("_data").append(i).append(endName);
                    System.out.println(sb.toString());
                    File file2 = new File(sb.toString());
                    //创建写文件的输出流
                    OutputStream out = new FileOutputStream(file2);
                    int len = -1;
                    byte[] bytes = new byte[10 * 1024 * 1024];
                    while ((len = in.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                        if (file2.length() > (l / m)) {
                            break;
                        }
                    }
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //文件合并的方法（传入要合并的文件路径）
    private static void joinFileDemo(String... src) {
        for (int i = 0; i < src.length; i++) {
            File file = new File(src[i]);
            String fileName = file.getName().substring(0, file.getName().indexOf("_"));
            String endName = file.getName().substring(file.getName().lastIndexOf("."));
            StringBuffer sb = new StringBuffer();
            sb.append(file.getParent()).append("\\").append(fileName)
                    .append(endName);
            System.out.println(sb.toString());
            try {
                //读取小文件的输入流
                InputStream in = new FileInputStream(file);
                //写入大文件的输出流
                File file2 = new File(sb.toString());
                OutputStream out = new FileOutputStream(file2, true);
                int len = -1;
                byte[] bytes = new byte[10 * 1024 * 1024];
                while ((len = in.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
                out.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件合并完成！");
    }
}
