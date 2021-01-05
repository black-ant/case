package com.gang.study.source.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Classname ClassPathResourceLogic
 * @Description TODO
 * @Date 2020/7/3 15:45
 * @Created by zengzg
 */
@Component
public class ClassPathResourceLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void get() throws Exception {
        ClassPathResource cpr = new ClassPathResource("other-test.txt");
        byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
        String data = new String(bdata, StandardCharsets.UTF_8);
        logger.info("------> this is data :{} <-------", data);
    }

    public void getYml() throws Exception {
        ClassPathResource cpr = new ClassPathResource("application.yml");
        //        new ByteArrayInputStream(cpr.getInputStream());
        byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
        String data = new String(bdata, StandardCharsets.UTF_8);
        logger.info("------> this is data :{} <-------", data);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        get();
        //        getYml();
    }
}
