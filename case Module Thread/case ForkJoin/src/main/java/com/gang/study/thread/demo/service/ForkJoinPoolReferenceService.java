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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Classname ForkJoinPoolReferenceService
 * @Description TODO
 * @Date 2021/2/23 11:23
 * @Created by zengzg
 */
@Service
public class ForkJoinPoolReferenceService extends RecursiveTask<Integer> implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private File file;
    private Integer salt;

    public ForkJoinPoolReferenceService() {
    }

    public ForkJoinPoolReferenceService(File file, Integer salt) {
        this.file = file;
        this.salt = salt;
    }

    @Override
    protected Integer compute() {
        return ForkFileUtils.read(file, salt);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "data";

        Long startTime = System.currentTimeMillis();
        filePath = filePath.replace("%20", " ");
        logger.info("------> Fork Join 读取文件开始 :{} <-------", filePath);
        File file = new File(filePath);
        List<File> files = FileUtil.loopFiles(file);

        Integer result = 0;
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        for (int i = 0; i < files.size(); i++) {
            ForkJoinPoolReferenceService rt = new ForkJoinPoolReferenceService(files.get(0), i);

            // 方式一 :
//            rt.fork();
//            result = result + rt.join();

            // 方式二 :
            ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(rt);
            result = result + forkJoinTask.get();
        }
        logger.info("------> this is result :{} <-------", result);

        Long endTime = System.currentTimeMillis();

        logger.info("------> Fork Join  读取文件结束 :{} <-------", endTime - startTime);
    }
}
