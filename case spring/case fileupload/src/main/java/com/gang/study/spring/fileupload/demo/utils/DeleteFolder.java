package com.gang.study.spring.fileupload.demo.utils;


import java.io.File;

public class DeleteFolder {
    /**
     * 删除指定文件夹
     *
     * @param folderPath 文件夹路径
     * @return 是否删除成功
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean deleteFolder(final String folderPath) {
        File dir = new File(folderPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return dir.delete();
    }
}
