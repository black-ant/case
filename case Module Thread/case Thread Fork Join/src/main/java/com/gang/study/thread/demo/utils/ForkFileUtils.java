package com.gang.study.thread.demo.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @Classname FileUtils
 * @Description TODO
 * @Date 2021/2/23 11:49
 * @Created by zengzg
 */
public class ForkFileUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 读取文件且计算
     *
     * @param read
     * @param salt
     * @return
     */
    public static Integer read(File read, Integer salt) {
        JSONObject object = JSONObject.parseObject(FileUtil.readString(read, "UTF-8"));
        Integer num1 = object.getInteger("num1");
        Integer num2 = object.getInteger("num2");
        Integer multiply = object.getInteger("multiply");

        // 模拟复杂运算
        Integer result = (num1 + num2) * multiply * salt;
        result = result + ((num1 + num2) * multiply * salt);
        result = result - ((num1 + num2) * multiply * salt);
        result = result + ((num1 + num2) * multiply * salt);
        result = result + ((num1 + num2) * multiply * salt);
        result = result - ((num1 + num2) * multiply * salt);
        result = result + ((num1 + num2) * multiply * salt);
        result = result + ((num1 + num2) * multiply * salt);
        result = result - ((num1 + num2) * multiply * salt);
        result = result + ((num1 + num2) * multiply * salt);

        // 模拟不释放锁
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return result;
    }
}
