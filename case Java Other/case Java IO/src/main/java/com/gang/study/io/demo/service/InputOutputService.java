package com.gang.study.io.demo.service;

import com.gang.study.io.demo.model.Properties;
import com.sun.org.apache.xpath.internal.operations.String;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class InputOutputService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run() {
        readStream();
    }


    /**
     * Stram 读流
     */
    public void readStream() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(Properties.PATH));
            StringBuffer backMsg = new StringBuffer();
            while (inputStream.read() != -1) {
                backMsg.append(inputStream.read());
            }

            logger.info("----> msg is :{}", backMsg.toString());

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * stream 写方法
     */
    public void writeStream() {

    }
}
