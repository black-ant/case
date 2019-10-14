package com.gang.study.io.demo.service;

import com.gang.study.io.demo.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

@Service
public class FileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() {
        logger.info("------> file is exists :{} <-- -----", checkFileExists("D:\\java\\workspace\\git\\case\\case Java Other\\case Java IO\\doc\\test.txt"));
        logger.info("------> file is exists :{} <-- -----", checkFileExists("D:\\java\\workspace\\git\\case\\case Java Other\\case Java IO\\doc", "test.txt"));
    }


    /**
     *
     */
    public void writeFile(String path) {

        if (checkFileExists(path)) {

        }
    }

    /**
     * 创建 File 文件
     */
    public void createFile(File file) {
        
    }

    /**
     * 查询文件是否存在 , 仅通过路径
     */
    public Boolean checkFileExists(File file) {
        return file.exists();
    }

    /**
     * 查询文件是否存在 , 仅通过路径
     */
    public Boolean checkFileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * 查询文件是否存在 ，通过上下文 及文件名（全限定）
     */
    public Boolean checkFileExists(String parent, String name) {
        return new File(parent, name).exists();
    }

    /**
     * 构建简单log
     *
     * @return
     */
    public String logDetail() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(DateUtil.formatDateTime(new Date())).append(" ")
                .append("[" + this.getClass().getSimpleName() + "#" + this.getClass().getPackage() + "]").append("-");

        return stringBuffer.toString();
    }

}
