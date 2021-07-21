package com.gang.study.thread.demo.service;

import cn.hutool.core.io.FileUtil;
import com.gang.study.thread.demo.utils.ForkFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @Classname FileSingleThreadService
 * @Description TODO
 * @Date 2021/2/23 11:48
 * @Created by zengzg
 */
@Service
public class FileSingleThreadService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "data";

        Long startTime = System.currentTimeMillis();
        filePath = filePath.replace("%20", " ");
        logger.info("------> 单线程读取文件开始 :{} <-------", filePath);
        File file = new File(filePath);
        Integer num = 1;
        List<File> files = FileUtil.loopFiles(file);

        Integer result = 0;
        for (int i = 0; i < files.size(); i++) {
            result = result + ForkFileUtils.read(files.get(i), i);
        }
        logger.info("------> this is result :{} <-------", result);

        Long endTime = System.currentTimeMillis();

        logger.info("------> 单线程读取文件结束 :{} <-------", endTime - startTime);
    }
}
