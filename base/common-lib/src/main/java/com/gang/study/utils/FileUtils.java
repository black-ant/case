package com.gang.study.utils;

import java.io.File;
import java.io.IOException;

/**
 * @Classname FileUtils
 * @Description TODO
 * @Date 2019/11/6 21:10
 * @Created by ant-black 1016930479@qq.com
 */
public class FileUtils {


    /**
     * 判断文件夹是否存在
     */
    public void fileExists(String url) {
        System.out.println(url);
        File dir = new File(url);
        // 判断文件夹是否存在
        if (dir.exists()) {
            if (dir.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            dir.mkdir();
        }
    }

    /**
     * check this path is exits
     * if not , create it and back
     *
     * @param filePath
     * @return
     */
    public static File isExistDirAndBack(String filePath) {
        isExistDir(filePath);
        return new File(filePath);
    }

    /**
     * 判断多级路径是否存在，不存在就创建
     *
     * @param filePath 支持带文件名的Path：如：D:\news\2014\12\abc.text，和不带文件名的Path：如：D:\news\2014\12
     */
    public static void isExistDir(String filePath) {
        String paths[] = {""};
        //切割路径
        try {
            String tempPath = new File(filePath).getCanonicalPath();//File对象转换为标准路径并进行切割，有两种windows和linux
            paths = tempPath.split("\\\\");//windows
            if (paths.length == 1) {
                paths = tempPath.split("/");
            }//linux
        } catch (IOException e) {
            System.out.println("切割路径错误");
            e.printStackTrace();
        }
        //判断是否有后缀
        boolean hasType = false;
        if (paths.length > 0) {
            String tempPath = paths[paths.length - 1];
            if (tempPath.length() > 0) {
                if (tempPath.indexOf(".") > 0) {
                    hasType = true;
                }
            }
        }
        //创建文件夹
        String dir = paths[0];
        for (int i = 0; i < paths.length - (hasType ? 2 : 1); i++) {// 注意此处循环的长度，有后缀的就是文件路径，没有则文件夹路径
            try {
                dir = dir + "/" + paths[i + 1];//采用linux下的标准写法进行拼接，由于windows可以识别这样的路径，所以这里采用警容的写法
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                    System.out.println("成功创建目录：" + dirFile.getCanonicalFile());
                }
            } catch (Exception e) {
                System.err.println("文件夹创建发生异常");
                e.printStackTrace();
            }
        }
    }
}
