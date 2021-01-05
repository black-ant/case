package com.gang.study.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname ReadUtils
 * @Description TODO
 * @Date 2019/11/6 21:21
 * @Created by ant-black 1016930479@qq.com
 */
public class ReadUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public <T> String redaFile(String type, String filePath, String fromLine, String endLine, T back) {
        return new String();
    }


    /**
     * 读取文本
     *
     * @param filepath
     */
    public String readFile(String filepath, int fromLineNum) {

        // TODO : 各种判断略

        File logFile = new File(filepath);
        StringBuilder logContentBuffer = new StringBuilder();
        int toLineNum = 0;
        LineNumberReader reader = null;
        try {
            //reader = new LineNumberReader(new FileReader(logFile));
            reader = new LineNumberReader(new InputStreamReader(new FileInputStream(logFile), Charset.forName("UTF-8")));
            String line = null;

            while ((line = reader.readLine()) != null) {
                toLineNum = reader.getLineNumber();        // [from, entity], start as 1
                if (toLineNum >= fromLineNum) {
                    logContentBuffer.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        logger.info("------> msg is :{} <-------", logContentBuffer.toString());
        return logContentBuffer.toString();
    }


    public Map<Integer, String> getFileByLineMap(String filepath, int fromLine, int endLine) {

        HashMap<Integer, String> backMap = new HashMap<>();
        File logFile = new File(filepath);
        LineNumberReader reader = null;
        int toLineNum = 0;

        try {
            //reader = new LineNumberReader(new FileReader(logFile));
            reader = new LineNumberReader(new InputStreamReader(new FileInputStream(logFile), Charset.forName("UTF-8")));
            String line = null;

            while ((line = reader.readLine()) != null) {
                toLineNum = reader.getLineNumber();        // [from, entity], start as 1
                if (toLineNum >= fromLine) {
                    backMap.put(toLineNum, line);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return backMap;
    }


}
