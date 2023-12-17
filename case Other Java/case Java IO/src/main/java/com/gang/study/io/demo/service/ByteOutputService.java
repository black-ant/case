package com.gang.study.io.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @Classname ByteOutputService
 * @Description TODO
 * @Date 2020/11/2 15:41
 * @Created by zengzg
 */
@Component
public class ByteOutputService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        byteRead();
//        byteRead2();
    }

    public void byteRead() throws Exception {
        String value = "123456789";
        final byte[] byteArray = value.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        StringBuffer backMsg = new StringBuffer();

        byte[] buffer = new byte[99];
        int count = 0;

        // 每次读取指定 byte 数目的数据 , 如果改成1 , 这个循环会走9次
        while ((count = inputStream.read(buffer)) > 0) {
            // 表示读出 byte 中的数据 , 从 0 到本次read 的长度
            backMsg.append(new String(buffer, 0, count));
        }

        logger.info("----> msg is :{}", backMsg.toString());
        logger.info("----> msg byte is :{}", new String(buffer));
    }

    public void byteRead2() throws Exception {
        String value = "123456789";
        final byte[] byteArray = value.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        byte[] buffer = new byte[1000];
        // 1000 个满了就超了
        inputStream.read(buffer);

        logger.info("----> msg byte is :{}", new String(buffer));
    }
}
