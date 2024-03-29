package com.gang.file.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Classname JavaFileService
 * @Description TODO
 * @Date 2021/7/23
 * @Created by zengzg
 */
@Component
public class JavaFileService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        readByPath();

        readByRelativePath();
    }


    private void readByPath() {
        File file = new File("D:\\java\\workspace\\git\\case\\case Other Java\\case file\\src\\main\\resources\\data\\Test.txt");

        logger.info("------> file exit ? [{}] <-------", file.exists());


    }

    private void readByRelativePath() {

    }
}
